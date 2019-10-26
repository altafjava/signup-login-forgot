package com.slf.main.service;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.slf.main.jwt.JwtUser;
import com.slf.main.jwt.JwtValidator;
import com.slf.main.model.Customer;
import com.slf.main.model.OTP;
import com.slf.main.repository.CustomerRepository;
import com.slf.main.repository.OtpRepository;
import com.slf.main.util.EmailUtil;
import com.slf.main.util.OTPEncryptorDecryptor;
import com.slf.main.util.OTPGenerator;
import com.slf.main.util.PasswordEncryptor;

@Service
public class ForgotPasswordService {

	private static final Logger LOGGER = LogManager.getLogger(SignupService.class);

	@Value("${forgotPasswordSMS}")
	private String forgotPasswordMessage;
	@Autowired
	private SignupService signupService;
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private OtpRepository otpRepository;
	@Autowired
	private JwtValidator jwtValidator;

	public ResponseEntity<String> forgotPassword(String mobile, String email, String otpType) {
		if (isMobileEmailExist(mobile, email)) {
			String generatedOtp = Integer.toString(OTPGenerator.generateOTP());
			Customer customer = customerRepository.findByMobileOrEmail(mobile, email);
			signupService.saveOTP(generatedOtp, mobile, email, otpType);
			String message = forgotPasswordMessage.replace("{OTP}", generatedOtp).replace("{USERNAME}", customer.getFirstName());
			LOGGER.info(message);
			boolean flag = false;
			if (mobile == null) {
				// String subject = "Forgot Password OTP";
				LOGGER.info("OTP sending in email " + email);
				// flag = emailUtil.sendMail(email, subject, message);
				flag = true;
			} else {
				LOGGER.info("OTP sending in mobile : " + mobile);
				// Here sent otp in mobile
				// GupshupSMS gupshupSMS = GupshupSMS.getInstance();
				// flag = gupshupSMS.sendSMS(mobile, message, GupshupCredentialType.TRANSACTIONAL.toString());
				flag = true;
			}
			if (flag)
				return new ResponseEntity<>("OTP generated successfully", HttpStatus.OK);
			else
				return new ResponseEntity<>("OTP generation failed", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("Mobile/Email does not exist", HttpStatus.FORBIDDEN);
	}

	public ResponseEntity<String> verifyOTP(String mobile, String email, String otp, String otpType) {
		String originalOTP = null;
		Customer customer = customerRepository.findByMobileOrEmail(mobile, email);
		if (customer != null) {
			String customerId = customer.getCustomerId();
			List<OTP> otpList = otpRepository.findByOtpTypeAndIsExpiredFalseOrderByUpdatedTimeDesc(otpType);
			if (!otpList.isEmpty()) {
				String encryptedOTP = otpList.get(0).getEncryptedOTP();
				if (encryptedOTP != null)
					originalOTP = OTPEncryptorDecryptor.decrypt(encryptedOTP);
				else
					return new ResponseEntity<>("Mobile OR Email is not valid", HttpStatus.FORBIDDEN);

				if (!otp.equals(originalOTP)) {
					LOGGER.warn("OTP is not valid mobile:" + mobile + "   email:" + email + "  otp:" + otp);
					return new ResponseEntity<>("OTP is not valid", HttpStatus.FORBIDDEN);
				}
				String token = signupService.createJwtUser(customerId);
				LOGGER.info("USER logged in : " + mobile);
				return new ResponseEntity<>(token, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Mobile/Email does not exist", HttpStatus.FORBIDDEN);
	}

	public ResponseEntity<String> resetPassword(String token, String newPassword) {
		JwtUser jwtUser = jwtValidator.validate(token);
		String customerId = jwtUser.getCustomerId();
		Customer customer = customerRepository.findByCustomerId(customerId);
		String encryptedNewPassword = PasswordEncryptor.encryptPassword(newPassword);
		if (encryptedNewPassword != null) {
			customer.setPassword(encryptedNewPassword);
			customerRepository.save(customer);
			return new ResponseEntity<>("Customer Password updated successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>("Password updation failed", HttpStatus.FORBIDDEN);
	}

	private boolean isMobileEmailExist(String mobile, String email) {
		Customer customer = customerRepository.findByMobileOrEmail(mobile, email);
		return customer == null ? false : true;
	}
}

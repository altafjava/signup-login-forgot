package com.slf.main.service;

import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.slf.main.dto.SignupDTO;
import com.slf.main.dto.SignupReturnDTO;
import com.slf.main.dto.VerifyOtpDTO;
import com.slf.main.enums.OtpTypeEnum;
import com.slf.main.jwt.JwtGenerator;
import com.slf.main.jwt.JwtUser;
import com.slf.main.model.Customer;
import com.slf.main.model.OTP;
import com.slf.main.repository.CustomerRepository;
import com.slf.main.repository.OtpRepository;
import com.slf.main.util.OTPEncryptorDecryptor;
import com.slf.main.util.OTPGenerator;
import com.slf.main.util.PasswordEncryptor;

@Service
public class SignupService {

	private static final Logger LOGGER = LogManager.getLogger(SignupService.class);

	@Value("${signupSMS}")
	private String signupSMS;
	@Value("${jwt.expirationInMS}")
	private int jwtExpirationInMs;
	@Autowired
	private JwtGenerator jwtGenerator;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private OtpRepository otpRepository;

	public ResponseEntity<?> signup(SignupDTO signupDTO) {

		String mobile = signupDTO.getMobile();
		String email = signupDTO.getEmail();
		Customer customer = customerRepository.findByMobileOrEmail(mobile, email);
		if (customer == null) {
//			generate otp
			String generatedOtp = Integer.toString(OTPGenerator.generateOTP());
			// save signup details into mongodb
			String customerId = saveSignupDetails(signupDTO, generatedOtp);
//			Here send sms to mobile and email
			String message = signupSMS.replace("{OTP}", generatedOtp);
			LOGGER.info(message);
//			save otp into mongo db
			String otpId = saveOTP(generatedOtp, mobile, email, OtpTypeEnum.SIGNUP.name());
			// return signupId & otpId to the UI
			SignupReturnDTO signupReturnDTO = new SignupReturnDTO();
			signupReturnDTO.setOtpId(otpId);
			signupReturnDTO.setCustomerId(customerId);
			return new ResponseEntity<>(signupReturnDTO, HttpStatus.OK);
		} else {
			String token = createJwtUser(customer.getCustomerId());
			return new ResponseEntity<>(token, HttpStatus.OK);
		}
	}

	public ResponseEntity<String> verifyOTP(VerifyOtpDTO verifyOtpDTO, String otpType) {
		String userOtp = verifyOtpDTO.getOtp();
		String encryptedOTP = OTPEncryptorDecryptor.encrypt(userOtp);
		OTP otp = otpRepository.findByIdAndOtpTypeAndIsExpiredFalse(verifyOtpDTO.getOtpId(), otpType);
		if (otp != null && otp.getEncryptedOTP().equals(encryptedOTP) && verifyOtpDTO.getMobile().equals(otp.getMobile()) && verifyOtpDTO.getEmail().equals(otp.getEmail())) {
			String customerId = verifyOtpDTO.getCustomerId();
			Customer customer = customerRepository.findByCustomerId(customerId);
			if (customer == null) {
				return new ResponseEntity<>("Wrong customerId", HttpStatus.FORBIDDEN);
			} else {
				String token = createJwtUser(customerId);
				return new ResponseEntity<>(token, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>("Invalid OTP", HttpStatus.FORBIDDEN);
		}
	}

	public String createJwtUser(String customerId) {
		JwtUser jwtUser = new JwtUser();
		jwtUser.setCustomerId(customerId);
		jwtUser.setIssuedAt(new Date());
		jwtUser.setExpiration(new Date(new Date().getTime() + jwtExpirationInMs));
		return jwtGenerator.generate(jwtUser);
	}

	private String saveSignupDetails(SignupDTO signupDTO, String generatedOtp) {
		Customer signup = new Customer();
		BeanUtils.copyProperties(signupDTO, signup);
		String encryptedPassword = PasswordEncryptor.encryptPassword(signupDTO.getPassword());
		signup.setPassword(encryptedPassword);
		signup = customerRepository.save(signup);
		return signup.getCustomerId();
	}

	public String saveOTP(String generatedOTP, String mobile, String email, String otpType) {
		String encryptedOTP = OTPEncryptorDecryptor.encrypt(generatedOTP);
		OTP otpDocument = new OTP();
		otpDocument.setCreatedTime(new Date());
		otpDocument.setUpdatedTime(new Date());
		otpDocument.setEncryptedOTP(encryptedOTP);
		otpDocument.setExpired(false);
		otpDocument.setMobile(mobile);
		otpDocument.setEmail(email);
		otpDocument.setOtpType(otpType);
		OTP otpResult = otpRepository.save(otpDocument);
		return otpResult.getId();
	}
}

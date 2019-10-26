package com.slf.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.slf.main.dto.SignupDTO;
import com.slf.main.dto.VerifyOtpDTO;
import com.slf.main.enums.OtpTypeEnum;
import com.slf.main.service.SignupService;

@RestController
public class SignupController {

	@Autowired
	private SignupService signupService;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody SignupDTO signupDTO) {
		return signupService.signup(signupDTO);
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<String> verifyOTP(@RequestBody VerifyOtpDTO verifyOtpDTO) {
		return signupService.verifyOTP(verifyOtpDTO, OtpTypeEnum.SIGNUP.name());
	}
}

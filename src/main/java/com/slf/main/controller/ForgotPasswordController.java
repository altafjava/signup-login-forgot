package com.slf.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.slf.main.enums.OtpTypeEnum;
import com.slf.main.service.ForgotPasswordService;

@RestController
public class ForgotPasswordController {

	@Value("${jwt.prefixLength}")
	private int jwtPrefixLength;
	@Autowired
	private ForgotPasswordService forgotPasswordService;
	
	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(String mobile, String email) {
		return forgotPasswordService.forgotPassword(mobile, email, OtpTypeEnum.FORGOT_PASSWORD.toString());
	}
	
	@PostMapping("/forgot/verify-otp")
	public ResponseEntity<String> verifyOTP(String mobile, String email, @RequestParam String otp) {
		// either mobile or email but otp is mandatory
		return forgotPasswordService.verifyOTP(mobile, email, otp, OtpTypeEnum.FORGOT_PASSWORD.toString());
	}

	@PostMapping("/reset-password/{newPassword}")
	public ResponseEntity<String> resetPassword(@RequestHeader("Authorization") String token, @PathVariable String newPassword) {
		return forgotPasswordService.resetPassword(token.substring(jwtPrefixLength), newPassword);
	}
}

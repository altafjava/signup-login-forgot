package com.slf.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.slf.main.dto.LoginDTO;
import com.slf.main.model.Customer;
import com.slf.main.service.LoginService;

@RestController
public class LoginController {

	@Value("${jwt.prefixLength}")
	private int jwtPrefixLength;
	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
		return loginService.login(loginDTO);
	}

	@GetMapping("/customer")
	public ResponseEntity<Customer> getCustomer(@RequestHeader("Authorization") String token) {
		return loginService.getCustomer(token.substring(jwtPrefixLength));
	}
}

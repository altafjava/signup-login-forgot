package com.slf.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.slf.main.dto.LoginDTO;
import com.slf.main.jwt.JwtUser;
import com.slf.main.jwt.JwtValidator;
import com.slf.main.model.Customer;
import com.slf.main.repository.CustomerRepository;
import com.slf.main.util.PasswordEncryptor;

@Service
public class LoginService {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SignupService signupService;
	@Autowired
	private JwtValidator jwtValidator;
	@Autowired
	private CustomerRepository customerRepository;

	public ResponseEntity<String> login(LoginDTO loginDTO) {
		String hashedPassword = PasswordEncryptor.encryptPassword(loginDTO.getPassword());
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(Criteria.where("password").is(hashedPassword),
				new Criteria().orOperator(Criteria.where("mobile").is(loginDTO.getMobile()), Criteria.where("email").is(loginDTO.getEmail()))));

		Customer customer = mongoTemplate.findOne(query, Customer.class);
		if (customer == null) {
			return new ResponseEntity<>("Credential is not valid", HttpStatus.FORBIDDEN);
		} else {
			String token = signupService.createJwtUser(customer.getCustomerId());
			return new ResponseEntity<>(token, HttpStatus.OK);
		}
	}

	public ResponseEntity<Customer> getCustomer(String token) {
		JwtUser jwtUser = jwtValidator.validate(token);
		String customerId = jwtUser.getCustomerId();
		Customer customer = customerRepository.findByCustomerId(customerId);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
}

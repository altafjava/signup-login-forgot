package com.slf.main.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.slf.main.model.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

	Customer findByMobileOrEmail(String mobile, String email);

	Customer findByCustomerId(String customerId);
}

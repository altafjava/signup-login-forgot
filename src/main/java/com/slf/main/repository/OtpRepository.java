package com.slf.main.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.slf.main.model.OTP;

@Repository
public interface OtpRepository extends MongoRepository<OTP, String> {

	List<OTP> findAllByMobileAndAndIsExpiredOrderByUpdatedTimeDesc(String mobile, boolean bool);

	List<OTP> findAllByIsExpired(boolean bool);

	OTP findByIdAndOtpTypeAndIsExpiredFalse(String id, String otpType);

	List<OTP> findByOtpTypeAndIsExpiredFalseOrderByUpdatedTimeDesc(String otpType);

}

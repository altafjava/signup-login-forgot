package com.slf.main.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "otp")
public class OTP {

	@Id
	private String id;
	private String encryptedOTP;
	private boolean isExpired;
	private String mobile;
	private String email;
	private String otpType;
	private String customerId;
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss a", timezone = "IST")
	private Date createdTime;
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss a", timezone = "IST")
	private Date updatedTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEncryptedOTP() {
		return encryptedOTP;
	}

	public void setEncryptedOTP(String encryptedOTP) {
		this.encryptedOTP = encryptedOTP;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtpType() {
		return otpType;
	}

	public void setOtpType(String otpType) {
		this.otpType = otpType;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

}

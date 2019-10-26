package com.slf.main.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PasswordEncryptor {

	private static final Logger LOGGER = LogManager.getLogger(PasswordEncryptor.class);

	private PasswordEncryptor() {
	}

	public static String encryptPassword(String rawPassword) {
		String hashedPassword = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(rawPassword.getBytes(), 0, rawPassword.length());
			hashedPassword = new BigInteger(1, digest.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e);
		}
		return hashedPassword;
	}
}

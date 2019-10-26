package com.slf.main.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OTPEncryptorDecryptor {

	private static final Logger LOGGER = LogManager.getLogger(OTPEncryptorDecryptor.class);

	private static SecretKeySpec secretKey;
	private static byte[] keyArray;
	static String myKey = "altaf";
	static {
		MessageDigest sha = null;
		try {
			keyArray = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			keyArray = sha.digest(keyArray);
			keyArray = Arrays.copyOf(keyArray, 16);
			secretKey = new SecretKeySpec(keyArray, "AES");
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			LOGGER.error(e);
		}

	}

	public static String encrypt(String otp) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(otp.getBytes("UTF-8")));
		} catch (Exception e) {
			LOGGER.error("Error while decrypting: " + e);
		}
		return null;
	}

	public static String decrypt(String encryptedOTP) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedOTP)));
		} catch (Exception e) {
			LOGGER.error("Error while decrypting: " + e);
		}
		return null;
	}
}

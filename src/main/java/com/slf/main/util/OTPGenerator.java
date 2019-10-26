package com.slf.main.util;

import java.util.concurrent.ThreadLocalRandom;

public class OTPGenerator {

	private OTPGenerator() {
	}

	public static int generateOTP() {
		return ThreadLocalRandom.current().nextInt(100000, 1000000);
	}
}

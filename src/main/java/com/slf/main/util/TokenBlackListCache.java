package com.slf.main.util;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TokenBlackListCache {

	private static final Logger LOGGER = LogManager.getLogger(TokenBlackListCache.class);

	private Map<String, Date> blackListTokensMap = new LinkedHashMap<>();

	// @Cacheable(value = "expiryDate", key = "#token")
	public boolean isTokenBlackListed(String token) {
		return blackListTokensMap.containsKey(token);
	}

	// @CachePut(value = "expiryDate", key = "#token")
	public void put(String token, Date expiryDate) {
		blackListTokensMap.put(token, expiryDate);
	}

	@Scheduled(cron = "${deleteExpiredTokenCronJobCycle}")
	public void deleteExpiredToken() {
		LOGGER.info("Black List Token Deleting");
		for (Map.Entry<String, Date> entry : blackListTokensMap.entrySet()) {
			Date expiryDate = entry.getValue();
			Date currenDate = new Date();
			long duration = expiryDate.getTime() - currenDate.getTime();
			System.out.println("Black List Token Time = " + TimeUnit.MILLISECONDS.toMinutes(duration));
			if (TimeUnit.MILLISECONDS.toMinutes(duration) > 30) {
				blackListTokensMap.remove(entry.getKey());
			}
		}
	}

}

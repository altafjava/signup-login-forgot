package com.slf.main.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {

	@Value("${jwt.secret}")
	private String jwtSecret;
	@Value("${jwt.expirationInMS}")
	private int jwtExpirationInMs;

	public String generate(JwtUser jwtUser) {
		Claims claims = Jwts.claims().setSubject(jwtUser.getCustomerId() + "");
		claims.setIssuedAt(new Date());
		claims.setExpiration(new Date(new Date().getTime() + jwtExpirationInMs));
		claims.put("posId", jwtUser.getPosId());
		claims.put("otp", jwtUser.getOtp());
		claims.put("role", jwtUser.getRole());
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		// returning the JWT Token
	}
}

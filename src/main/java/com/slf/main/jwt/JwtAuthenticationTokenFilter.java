package com.slf.main.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.slf.main.exception.JWTMissingException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

	@Value("${jwt.prefix}")
	private String jwtPrefix;
	@Value("${jwt.prefixLength}")
	private int jwtPrefixLength;

	public JwtAuthenticationTokenFilter() {
		super("/secure/**");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws AuthenticationException, IOException, ServletException {
		String headerToken = httpServletRequest.getHeader("Authorization");
		if (headerToken == null || !headerToken.startsWith(jwtPrefix + " ")) {
			throw new JWTMissingException("JWT Token is missing");
		}
		String token = headerToken.substring(jwtPrefixLength);
		JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);
		return getAuthenticationManager().authenticate(jwtAuthenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
			throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}
}
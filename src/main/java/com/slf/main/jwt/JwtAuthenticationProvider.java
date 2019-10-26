package com.slf.main.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private JwtValidator validator;

	@Override
	public boolean supports(Class<?> clazz) {
		return (JwtAuthenticationToken.class.isAssignableFrom(clazz));
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;
		String token = jwtAuthenticationToken.getToken();
		JwtUser jwtUser = validator.validate(token);
		if (jwtUser == null) {
			throw new RuntimeException("JWT Token is incorrect");
		}
		return new JwtUserDetails(jwtUser.getCustomerId(), jwtUser.getIssuedAt(), jwtUser.getExpiration(), token, jwtUser.getOtp());
		// List<GrantedAuthority> grantedAuthorities =
		// AuthorityUtils.commaSeparatedStringToAuthorityList(jwtUser.getRole());
		// return new JwtUserDetails(jwtUser.getCreatedTime(), jwtUser.getMobileNo(),
		// jwtUser.getCustomerId(), token);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
	}

}

package com.slf.main.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Date;

public class JwtUserDetails implements UserDetails {

	private static final long serialVersionUID = 2483170093697337488L;
	private String customerId;
	private Date issuedAt;
	private Date expiration;
	private String token;
	private String otp;
	// private String role;
	// private Collection<? extends GrantedAuthority> authorities;

	public JwtUserDetails() {
		super();
	}

	public JwtUserDetails(String customerId, Date issuedAt, Date expiration, String token, String otp) {
		super();
		this.customerId = customerId;
		this.issuedAt = issuedAt;
		this.expiration = expiration;
		this.token = token;
		this.otp = otp;
	}

	public Date getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(Date issuedAt) {
		this.issuedAt = issuedAt;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public String toString() {
		return "JwtUserDetails [customerId=" + customerId + ", issuedAt=" + issuedAt + ", expiration=" + expiration + ", token=" + token + ", otp=" + otp + "]";
	}

}

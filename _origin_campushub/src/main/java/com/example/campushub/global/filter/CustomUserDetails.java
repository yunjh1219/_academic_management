package com.example.campushub.global.filter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.campushub.user.domain.Type;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {

	private String username;
	private Type type;
	private Collection<? extends GrantedAuthority> authorities;

	@Builder
	private CustomUserDetails(String username, Type type, Collection<? extends GrantedAuthority> authorities) {
		this.username = username;
		this.type = type;
		this.authorities = authorities;
	}

	public static CustomUserDetails of(String username, Type type, Collection<? extends GrantedAuthority> authorities) {
		return CustomUserDetails.builder()
			.username(username)
			.type(type)
			.authorities(authorities)
			.build();
	}

	public static CustomUserDetails of(Claims claims) {
		return CustomUserDetails.builder()
			.username(claims.getSubject())
			.type(Type.fromKey(claims.get("type", String.class)))
			.authorities(convertAuthorities(claims.get("role", String.class)))
			.build();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return username;
	}

	private static List<? extends GrantedAuthority> convertAuthorities(String... roles) {
		return Arrays.stream(roles)
			.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
			.toList();
	}

}

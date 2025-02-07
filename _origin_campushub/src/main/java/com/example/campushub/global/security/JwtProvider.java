package com.example.campushub.global.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.campushub.global.filter.CustomUserDetails;
import com.example.campushub.user.domain.Role;
import com.example.campushub.user.domain.Type;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@Component
@Getter
public class JwtProvider {

	private final Key secretKey;
	private final Long accessTokenExpirationPeriod;
	private final String accessHeader;
	private final Long refreshTokenExpirationPeriod;
	private final String refreshHeader;
	private static final String BEARER = "Bearer ";

	public JwtProvider(@Value("${jwt.secretKey}") String secretKey,
		@Value("${jwt.access.expiration}") Long accessTokenExpirationPeriod,
		@Value("${jwt.access.header}") String accessHeader,
		@Value("${jwt.refresh.expiration}") Long refreshTokenExpirationPeriod,
		@Value("${jwt.refresh.header}") String refreshHeader) {

		this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		this.accessTokenExpirationPeriod = accessTokenExpirationPeriod;
		this.accessHeader = accessHeader;
		this.refreshTokenExpirationPeriod = refreshTokenExpirationPeriod;
		this.refreshHeader = refreshHeader;
	}

	public Token createToken(String userNum, Type type, Role role) {
		AccessToken accessToken = AccessToken.builder()
			.header(accessHeader)
			.data(createAccessToken(userNum, type, role))
			.build();
		RefreshToken refreshToken = RefreshToken.builder()
			.header(refreshHeader)
			.data(createRefreshToken())
			.build();

		return Token.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	public Optional<String> extractBearerToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(accessHeader))
			.filter(token -> token.startsWith(BEARER))
			.map(token -> token.replace(BEARER, ""));
	}

	public String getUsernameFromToken(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = CustomUserDetails.of(extractToken(token));

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	public Claims extractToken(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (Exception e) {
			return null;
		}
	}

	private String createAccessToken(String userNum, Type type, Role role) { //Type type
		return Jwts.builder()
			.setClaims(Map.of("type", type, "role", role))
			.setSubject(userNum)
			.setExpiration(expireTime(accessTokenExpirationPeriod))
			.signWith(secretKey)
			.compact();
	}

	private String createRefreshToken() {
		return Jwts.builder()
			.setExpiration(expireTime(refreshTokenExpirationPeriod))
			.signWith(secretKey)
			.compact();
	}

	private Date expireTime(Long expirationPeriod) {
		return new Date(System.currentTimeMillis() + expirationPeriod);
	}

}

package com.example.campushub.global.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.campushub.global.security.JwtProvider;
import com.example.campushub.user.service.ApiUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;
	private final ApiUserDetailsService apiUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String token = jwtProvider.extractToken(request)
			.filter(jwtProvider::isTokenValid)
			.orElse(null);

		if (token != null) {
			String username = jwtProvider.getUsernameFromToken(token);

			try {
				UserDetails userDetails = apiUserDetailsService.loadUserByUsername(username);

				if (userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UsernamePasswordAuthenticationToken authenticationToken =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			} catch (AuthenticationException e) {
				filterChain.doFilter(request, response);
				return;
			}

		}
		filterChain.doFilter(request, response);
	}
}

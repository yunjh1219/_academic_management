package com.example.campushub.global.util;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.example.campushub.global.error.exception.NotFoundRefreshToken;
import com.example.campushub.global.security.RefreshToken;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class RefreshTokenArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String REFRESH_TOKEN_COOKIE_NAME = "Authorization-refresh";

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(RefreshToken.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(REFRESH_TOKEN_COOKIE_NAME)) {
					String refreshTokenValue = cookie.getValue();

					return RefreshToken.builder()
						.header(REFRESH_TOKEN_COOKIE_NAME)
						.data(refreshTokenValue)
						.build();
				}
			}
		}

		throw new NotFoundRefreshToken();
	}
}

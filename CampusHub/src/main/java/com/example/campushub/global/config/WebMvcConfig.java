package com.example.campushub.global.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.campushub.global.util.LoginUserArgumentResolver;
import com.example.campushub.global.util.RefreshTokenArgumentResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new RefreshTokenArgumentResolver());
		resolvers.add(new LoginUserArgumentResolver());
	}


}

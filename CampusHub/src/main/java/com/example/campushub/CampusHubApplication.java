package com.example.campushub;

import org.aspectj.lang.annotation.After;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.campushub.dept.domain.Dept;
import com.example.campushub.dept.repository.DeptRepository;
import com.example.campushub.global.security.AccessToken;
import com.example.campushub.global.security.JwtProvider;
import com.example.campushub.user.domain.Role;
import com.example.campushub.user.domain.Type;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class CampusHubApplication {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final DeptRepository deptRepository;
	private final JwtProvider jwtProvider;

	public static void main(String[] args) {
		SpringApplication.run(CampusHubApplication.class, args);
	}



}

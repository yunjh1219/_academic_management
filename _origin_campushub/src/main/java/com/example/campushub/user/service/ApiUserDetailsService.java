package com.example.campushub.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.campushub.user.domain.User;
import com.example.campushub.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ApiUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userNum) throws UsernameNotFoundException {
		User user = userRepository.findByUserNum(userNum)
			.orElseThrow(() -> new UsernameNotFoundException("일치하는 학번을 찾을 수 없습니다."));

		return org.springframework.security.core.userdetails.User.builder()
			.username(user.getUserNum())
			.password(user.getPassword())
			.roles(user.getRole().name())
			.build();
	}
}

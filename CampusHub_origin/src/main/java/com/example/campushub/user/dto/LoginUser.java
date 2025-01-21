package com.example.campushub.user.dto;


import com.example.campushub.user.domain.Role;
import com.example.campushub.user.domain.Type;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginUser {
	private String userNum;
	private Role role;
	private Type type;

	@Builder
	public LoginUser(String userNum, Role role, Type type) {
		this.userNum = userNum;
		this.role = role;
		this.type = type;
	}
}

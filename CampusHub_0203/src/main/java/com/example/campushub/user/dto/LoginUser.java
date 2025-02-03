package com.example.campushub.user.dto;


import com.example.campushub.user.domain.Role;
import com.example.campushub.user.domain.Type;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginUser {
	private String userNum;
	private Type type;
	private Role role;

	@Builder
	public LoginUser(String userNum, Type type, Role role) {
		this.userNum = userNum;
		this.type = type;
		this.role = role;
	}
}

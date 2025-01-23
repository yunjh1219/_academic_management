package com.example.campushub.user.dto;

import com.example.campushub.user.domain.Status;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSearchCondition {
	private String deptName;
	private String userNum;
	private Status status;

	@Builder
	public UserSearchCondition(String deptName, String userNum, Status status) {
		this.deptName = deptName;
		this.userNum = userNum;
		this.status = status;
	}

}

/**
 * localhost:8080/users?deptName=hello&userNum=3&status=A
 * localhost:8080/users?deptName=hello
 */

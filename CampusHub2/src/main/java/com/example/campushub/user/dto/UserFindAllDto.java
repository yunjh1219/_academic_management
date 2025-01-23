package com.example.campushub.user.dto;

import com.example.campushub.user.domain.Status;
import com.example.campushub.user.domain.Type;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserFindAllDto {

	private Long userId;
	private String userNum;
	private String username;
	private String deptName;
	private Type type;
	private Status status;

	@Builder
	@QueryProjection
	public UserFindAllDto(Long userId, String userNum, String username, String deptName, Type type, Status status) {
		this.userId = userId;
		this.userNum = userNum;
		this.username = username;
		this.deptName = deptName;
		this.type = type;
		this.status = status;
	}
}

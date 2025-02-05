package com.example.campushub.user.dto;

import com.example.campushub.user.domain.Type;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequestDto {
		@NotBlank(message = "학번을 입력해주세요")
		private String userNum;

		@NotBlank(message = "비밀번호를 입력해주세요")
		private String password;

		@NotBlank(message = "사용자 타입을 선택해주세요")
		private Type type;

		@Builder
		public LoginRequestDto(String userNum, String password, Type type) {
			this.userNum = userNum;
			this.password = password;
			this.type = type;
		}
}

package com.example.campushub.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PasswordChangeRequest {

	@NotBlank(message = "기존 비밀번호를 입력해주세요")
	private String currentPassword;

	@NotBlank(message = "새 비밀번호를 입력해주세요")
	private String newPassword;

}

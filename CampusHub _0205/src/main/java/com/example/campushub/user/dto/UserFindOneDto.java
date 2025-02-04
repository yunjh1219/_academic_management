package com.example.campushub.user.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.campushub.user.domain.Grade;
import com.example.campushub.user.domain.Status;
import com.example.campushub.user.domain.Type;
import com.querydsl.core.annotations.QueryProjection;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class UserFindOneDto {

		private String userNum;
		private String userName;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDateTime birthday;
		private String deptName;
		@Nullable
		private String grade;
		private String email;
		private String phone;
		private String address;
		private String type;
		private String status;


		@Builder
		@QueryProjection
		public UserFindOneDto(String userNum, String userName, LocalDateTime birthday, String deptName, Grade grade, String email, String phone, String address, Type type, Status status) {
			this.userNum = userNum;
			this.userName = userName;
			this.birthday = birthday;
			this.deptName = deptName;
			this.grade = grade.getMessage();
			this.email = email;
			this.phone = phone;
			this.address = address;
			this.type = type.getKey();
			this.status = status.getMessage();
		}


	}

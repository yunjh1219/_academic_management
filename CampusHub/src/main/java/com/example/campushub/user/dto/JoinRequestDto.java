package com.example.campushub.user.dto;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.validation.constraints.NotBlank;

import com.example.campushub.dept.domain.Dept;
import com.example.campushub.dept.repository.DeptRepository;
import com.example.campushub.user.domain.Grade;
import com.example.campushub.user.domain.Role;
import com.example.campushub.user.domain.Status;
import com.example.campushub.user.domain.Type;
import com.example.campushub.user.domain.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class JoinRequestDto {

	DeptRepository deptRepository;

	@NotBlank(message = "이름을 입력하세요")
	private String userName;
	@NotBlank(message = "생년월일을 입력하세요")
	private LocalDateTime birthday;
	@NotBlank(message = "학과를 선택하세요")
	private String deptName;
	@NotBlank(message = "이메일을 입력하세요")
	private String email;
	@NotBlank(message = "연락처를 입력하세요")
	private String phone;
	@NotBlank(message = "주소를 입력하세요")
	private String address;
	@NotBlank(message = "학번을 입력해주세요")
	private String userNum;
	@NotBlank(message = "직책을 입력해주세요")
	private String type;
	@NotBlank(message = "비밀번호를 입력해주세요")
	private String password = "1234";

	@Builder
	public JoinRequestDto(String userName, LocalDateTime birthday, String dept, String email, String phone, String address, String userNum, String type) {
		this.userName = userName;
		this.birthday = birthday;
		this.deptName = dept;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.userNum = userNum;
		this.type = type;
		this.password = "1234"; //this.password = password;
	}

	public void passwordEncryption(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}

	public User toStudentEntity(Dept dept) {
		return User.builder()
			.userName(userName)
			.birthday(birthday)
			.dept(dept)
			.email(email)
			.phone(phone)
			.address(address)
			.userNum(userNum)
			.password(password)
			.role(Role.USER)
			.type(Type.STUDENT)
			.grade(Grade.FIRST_GRADE)
			.status(Status.ENROLLED)
			.build();
	}

	public User toProfessorEntity(Dept dept) {
		return User.builder()
			.userName(userName)
			.birthday(birthday)
			.dept(dept)
			.email(email)
			.phone(phone)
			.address(address)
			.userNum(userNum)
			.password(password)
			.role(Role.USER)
			.type(Type.PROFESSOR)
			.status(Status.EMPLOYED)
			.build();
	}

}
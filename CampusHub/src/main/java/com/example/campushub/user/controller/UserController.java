package com.example.campushub.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.campushub.global.common.SuccessResponse;
import com.example.campushub.global.security.Login;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.dto.QUserFindAllDto;
import com.example.campushub.user.dto.UserFindAllDto;
import com.example.campushub.user.dto.UserFindOneDto;
import com.example.campushub.user.dto.UserSearchCondition;
import com.example.campushub.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	//학생 단건조회
	@GetMapping("/api/student/{userNum}")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<UserFindOneDto> getStudent(@Login LoginUser loginUser, @PathVariable String userNum, Model model){
		return SuccessResponse.<UserFindOneDto>builder()
			.status(200)
			.message("학생 단건 조회 성공")
			.data(userService.getStudentByUserNum(loginUser, userNum))
			.build();
	}
	//학생 전체 조히
	@GetMapping("/api/students/condition")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<List<UserFindAllDto>> getStudentCondition(@Login LoginUser loginUser, UserSearchCondition condition, Model model){
		return SuccessResponse.<List<UserFindAllDto>>builder()
			.status(200)
			.message("학생 조건 전체 조회 성공")
			.data(userService.getStudentByCondition(loginUser, condition))
			.build();
	}
	//교수 단건조회
	@GetMapping("/api/professor/{userNum}")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<UserFindOneDto> getProfessor(@Login LoginUser loginUser, @PathVariable String userNum, Model model){
		UserFindOneDto responseData = userService.getProfessorByUserNum(loginUser, userNum);
		model.addAttribute("professor", responseData);
		return SuccessResponse.<UserFindOneDto>builder()
			.status(200)
			.message("교수 단건 조회성공")
			.data(userService.getProfessorByUserNum(loginUser, userNum))
			.build();
	}
	//교수 전체 조회
	@GetMapping("/api/professors/condition")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<List<UserFindAllDto>> getProfessorCondition(@Login LoginUser loginUser, UserSearchCondition condition, Model model){
		return SuccessResponse.<List<UserFindAllDto>>builder()
			.status(200)
			.message("교수 조건 전체 조회")
			.data(userService.getProfessorByCondition(loginUser, condition))
			.build();
	}

	//학생 상태 수락(변경)
	@PostMapping("/api/student/editstatus")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<Void> updateStudentStatus(@Login LoginUser loginUser, List<String> userNums){
		userService.updateUserStatus(loginUser, userNums);
		return SuccessResponse.<Void>builder()
			.status(200)
			.message("학생 상태변경 신청 수락 성공")
			.build();
	}

	//학생 휴학,복학 신청
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<Void> applyStudentStatus(@Login LoginUser loginUser){
		userService.updateUserStatusApply(loginUser, loginUser.getUserNum());
		return SuccessResponse.<Void>builder()
			.status(200)
			.message("학생 상태변경 신청 수락")
			.build();
	}
}

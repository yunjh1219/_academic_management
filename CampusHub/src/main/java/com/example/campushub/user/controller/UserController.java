package com.example.campushub.user.controller;

import java.util.List;

import com.example.campushub.user.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.campushub.global.common.SuccessResponse;
import com.example.campushub.global.security.Login;
import com.example.campushub.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	//로그인 학생의 단건 조회
	@GetMapping("/api/student")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<UserFindOneDto> getUserByUserNum(@Login LoginUser loginUser){
		return SuccessResponse.<UserFindOneDto>builder()
			.status(200)
			.message("로그인 학생 단건 조회 성곧")
			.data(userService.getUserByUserNum(loginUser))
			.build();
	}

	//학생 단건조회
	@GetMapping("/api/admin/student/{userNum}")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<UserFindOneDto> getStudent(@Login LoginUser loginUser, @PathVariable String userNum){
		return SuccessResponse.<UserFindOneDto>builder()
			.status(200)
			.message("학생 단건 조회 성공")
			.data(userService.getStudentByUserNum(loginUser, userNum))
			.build();
	}
	//학생 전체 조히
	@GetMapping("/api/admin/students/condition")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<List<UserFindAllDto>> getStudentCondition(@Login LoginUser loginUser, UserSearchCondition condition){
		return SuccessResponse.<List<UserFindAllDto>>builder()
			.status(200)
			.message("학생 조건 전체 조회 성공")
			.data(userService.getStudentByCondition(loginUser, condition))
			.build();
	}
	//교수 단건조회
	@GetMapping("/api/admin/professor/{userNum}")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<UserFindOneDto> getProfessor(@Login LoginUser loginUser, @PathVariable String userNum){
		return SuccessResponse.<UserFindOneDto>builder()
			.status(200)
			.message("교수 단건 조회성공")
			.data(userService.getProfessorByUserNum(loginUser, userNum))
			.build();
	}
	//교수 전체 조회
	@GetMapping("/api/admin/professors/condition")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<List<UserFindAllDto>> getProfessorCondition(@Login LoginUser loginUser, UserSearchCondition condition){
		return SuccessResponse.<List<UserFindAllDto>>builder()
			.status(200)
			.message("교수 조건 전체 조회")
			.data(userService.getProfessorByCondition(loginUser, condition))
			.build();
	}

	//학생 상태 수락(변경)
	@PostMapping("/api/admin/student/editstatus")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<Void> updateStudentStatus(@Login LoginUser loginUser, @RequestBody List<String> userNums){
		userService.updateUserStatus(loginUser, userNums);
		return SuccessResponse.<Void>builder()
			.status(200)
			.message("학생 상태변경 신청 수락 성공")
			.build();
	}

	//학생 휴학,복학 신청
	@PostMapping("/api/student/tuition/applystatus")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<Void> applyStudentStatus(@Login LoginUser loginUser){
		userService.updateUserStatusApply(loginUser);
		return SuccessResponse.<Void>builder()
			.status(200)
			.message("학생 상태변경 신청 수락")
			.build();
	}

	//사용자 마이페이지 정보 수정
	@PostMapping("/api/student/mypage")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<Void> updateUserInfo(@Login LoginUser loginUser, @RequestBody@Valid UpdateUserInfoDto updatedto){
		userService.updateUserInfo(loginUser,updatedto);
		return SuccessResponse.<Void>builder()
				.status(200)
				.message("사용자 정보 변겅 성공")
				.build();
	}

	//학생 삭제
	@DeleteMapping("/api/admin/user/{userNum}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public SuccessResponse<Void> deleteStudent(@Login LoginUser loginUser, @PathVariable String userNum){
		userService.deleteStudentByUserNum(loginUser, userNum);
		return SuccessResponse.<Void>builder()
			.status(200)
			.message("유저 삭제 성공")
			.build();
	}


}

package com.example.campushub.course.controller;

import java.util.List;

import com.example.campushub.course.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.campushub.course.service.CourseService;
import com.example.campushub.global.common.SuccessResponse;
import com.example.campushub.global.security.Login;
import com.example.campushub.user.dto.LoginUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CourseController {

	private final CourseService courseService;

	//교수 전체 + 컨디션 강의조회
	@GetMapping("/api/professor/course/all")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<List<CourseResponseDto>> findAllByProfCondition(@Login LoginUser loginUser, ProfCourseSearchCondition cond) {

		return SuccessResponse.<List<CourseResponseDto>>builder()
			.status(HttpStatus.OK.value())
			.message("교수 전체 강의 조회성공")
			.data(courseService.findAllByProfCondition(loginUser, cond))
			.build();
	}

	//학생 전체 + 컨디션 강의조회
	@GetMapping("/api/student/course/all")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<List<CourseResponseDto>> findAllByStudCondition(@Login LoginUser loginUser, StudCourseSearchCondition cond) {

		return SuccessResponse.<List<CourseResponseDto>>builder()
			.status(HttpStatus.OK.value())
			.message("학생 전체 강의 조회성공")
			.data(courseService.findAllByStudCondition(loginUser, cond))
			.build();
	}

	//교수 본인 강의조회
	@GetMapping("/api/professor/course")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<List<CourseResponseDto>> findAllByProf(@Login LoginUser loginUser) {

		return SuccessResponse.<List<CourseResponseDto>>builder()
			.status(HttpStatus.OK.value())
			.message("교수 본인 강의 조회성공")
			.data(courseService.findAllByProf(loginUser))
			.build();
	}

	//학생 본인 강의조회
	@GetMapping("/api/student/course")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<List<CourseResponseDto>> findAllByStud(@Login LoginUser loginUser) {

		return SuccessResponse.<List<CourseResponseDto>>builder()
			.status(HttpStatus.OK.value())
			.message("학생 본인 강의 조회성공")
			.data(courseService.findAllByStud(loginUser))
			.build();
	}

	//학생 수강 신청
	@PostMapping("/api/student/course/join")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<Void> joinCourse(@Login LoginUser loginUser, @RequestBody List<Long> courseIds) {

		courseService.joinCourse(loginUser, courseIds);

		return SuccessResponse.<Void>builder()
			.status(HttpStatus.OK.value())
			.message("학생 수강신청 성공")
			.build();
	}
	//강의 생성
	@PostMapping("/api/professor/course")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse<Void> createCourse(@Login LoginUser loginUser, @RequestBody @Valid CourseCreateDto createDto) {

		courseService.createCourse(loginUser, createDto);

		return SuccessResponse.<Void>builder()
			.status(HttpStatus.CREATED.value())
			.message("강의 생성성공")
			.build();
	}

	// .todo 강의 삭제를 만들까 말까 ㅋ.ㅋ
	//강의 삭제

	//강의 수정
	@PatchMapping("/api/professor/course/{courseId}")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<Void> editCourse(@Login LoginUser loginUser, @PathVariable Long courseId
	, @RequestBody @Valid CourseEditDto editDto) {

		courseService.editCourse(loginUser, courseId, editDto);

		return SuccessResponse.<Void>builder()
			.status(HttpStatus.OK.value())
			.message("강의 수정 성공")
			.build();
	}

	//교수 강의 시간표 조회
	@GetMapping("/api/professor/course/calender")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<List<CourseCalenderDto>> getProfessorCalender(@Login LoginUser loginUser) {
		return SuccessResponse.<List<CourseCalenderDto>>builder()
				.status(200)
				.data(courseService.findCalByProf(loginUser))
				.message("교수 강의 시간표 조회 성공")
				.build();
	}

	//학생 강의 시간표 조회
	@GetMapping("/api/student/course/calender")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<List<CourseCalenderDto>> getStudentCalender(@Login LoginUser loginUser) {
		return SuccessResponse.<List<CourseCalenderDto>>builder()
				.status(200)
				.data(courseService.findCalByStudent(loginUser))
				.message("학생 강의시간표 조회 성공")
				.build();
	}
}

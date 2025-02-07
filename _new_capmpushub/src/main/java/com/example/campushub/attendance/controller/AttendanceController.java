package com.example.campushub.attendance.controller;


import com.example.campushub.attendance.dto.*;
import com.example.campushub.attendance.service.AttendanceService;
import com.example.campushub.global.common.SuccessResponse;
import com.example.campushub.global.security.Login;
import com.example.campushub.user.dto.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    //강의별 출석 조회(교수)
    @GetMapping("/api/professor/attendance/condition")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<List<AttendanceResponseDto>> findUserAttendanceByCourse(@Login LoginUser loginUser, AttendanceSearchCondition cond) {
        System.out.println(cond.getCourseName());
        System.out.println(cond.getWeek());
        return SuccessResponse.<List<AttendanceResponseDto>>builder()
                .status(200)
                .message("강의별 출석 조회 성공")
                .data(attendanceService.findAttendance(cond,loginUser))
                .build();
    }


    //주차별 출결 기입
    @PostMapping("/api/professor/attendance/condition")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<Void> decideAttendance(@Login LoginUser loginUser,
                                                  @RequestBody List<AttendanceRequestDto> requestDto,
                                                    AttendanceSearchCondition cond) {

        attendanceService.createAttendance(loginUser,requestDto,cond);

        return SuccessResponse.<Void>builder()
                .status(200)
                .message("학생 주차별 출석 등록 완료")
                .build();

    }

    //강의별 출석 통계 조회(학생)
    @GetMapping("/api/professor/attendance/allcondition")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<List<AttendanceSummaryDto>> findAllAttendance(@Login LoginUser loginUser, @ModelAttribute AttendanceSearchCourseCondition cond) {
        return SuccessResponse.<List<AttendanceSummaryDto>>builder()
                .status(200)
                .message("강의별 출석 통계 조회 성공")
                .data(attendanceService.findAllAttendance(loginUser,cond))
                .build();

    }

    //본인 출석조회(학생)
    @GetMapping("/api/student/attendance/condition")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<List<AttendanceUserDto>> getMyAttendance(@Login LoginUser loginUser, @RequestParam String courseName) {
        return SuccessResponse.<List<AttendanceUserDto>>builder()
                .status(200)
                .message("사용자 강의별 출석 조회 성공")
                .data(attendanceService.userAttendance(loginUser,courseName))
                .build();
    }




}

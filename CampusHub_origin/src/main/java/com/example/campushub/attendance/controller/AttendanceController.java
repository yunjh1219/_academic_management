package com.example.campushub.attendance.controller;


import com.example.campushub.attendance.dto.AttendanceResponseDto;
import com.example.campushub.attendance.dto.AttendanceSearchCondition;
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

    @GetMapping("/api/professor/attendance/condition")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<List<AttendanceResponseDto>> findUserAttendanceByCourse(@Login LoginUser loginUser, AttendanceSearchCondition attendanceCond) {
        return SuccessResponse.<List<AttendanceResponseDto>>builder()
                .status(200)
                .message("강의별 출석 조회 성공")
                .data(attendanceService.findAttendance(attendanceCond,loginUser))
                .build();
    }

    @PostMapping("/api/professor/attendance/condition")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<Void> decideAttendance(@Login LoginUser loginUser,
                                                  @RequestBody @Valid List<AttendanceResponseDto> attendanceDto,
                                                  @RequestBody @Valid AttendanceSearchCondition attendanceCond) {

        attendanceService.createAttendance(loginUser,attendanceDto,attendanceCond);

        return SuccessResponse.<Void>builder()
                .status(200)
                .message("학생 주차별 출석 등록 완료")
                .build();

    }


}

package com.example.campushub.semsterschedule.controller;


import com.example.campushub.global.common.SuccessResponse;
import com.example.campushub.global.security.Login;
import com.example.campushub.semsterschedule.dto.SemesterScheduleRequest;
import com.example.campushub.semsterschedule.dto.SemesterScheduleResponse;
import com.example.campushub.semsterschedule.service.SemesterScheduleService;
import com.example.campushub.user.dto.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SemesterScheduleController {

    private final SemesterScheduleService semesterScheduleService;

    //월 학사일정 조회
    @GetMapping("/api/admin/semesterschedule/month")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<List<SemesterScheduleResponse>> getSemesterScheduleByMonth(@Login LoginUser loginUser, @RequestParam int year, @RequestParam int month ) {
        return SuccessResponse.<List<SemesterScheduleResponse>>builder()
                .status(200)
                .message("월 학사일정 조회 성공")
                .data(semesterScheduleService.getSchedulesByMonth(year, month, loginUser))
                .build();


    }
        //특정 날짜의 학사 일정 조회
    @GetMapping("/api/admin/semesterschedule/month/date")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<List<SemesterScheduleResponse>> getSemesterScheduleByDate(@Login LoginUser loginUser, @RequestParam LocalDateTime date) {
        return SuccessResponse.<List<SemesterScheduleResponse>>builder()
                .status(200)
                .message("특정 날짜 학사일정 조회 성공")
                .data(semesterScheduleService.getSchedulesByDate(date, loginUser))
                .build();
    }

    //학사 등록
    @PostMapping("/api/admin/semesterschedule")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<Void> createSemesterSchedule(@Login LoginUser loginUser, @RequestBody @Valid SemesterScheduleRequest request){
        semesterScheduleService.createSemesterSchedule(request,loginUser);

        return SuccessResponse.<Void>builder()
                .status(200)
                .message("학사일정 등록 성공")
                .build();
    }

}

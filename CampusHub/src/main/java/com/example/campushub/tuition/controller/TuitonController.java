package com.example.campushub.tuition.controller;


import com.example.campushub.global.common.SuccessResponse;
import com.example.campushub.global.security.Login;
import com.example.campushub.tuition.dto.TuitionFindAllResponse;
import com.example.campushub.tuition.dto.TuitionSearchCondition;
import com.example.campushub.tuition.service.TuitionService;
import com.example.campushub.user.dto.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TuitonController {

    private final TuitionService tuitionService;


    @GetMapping("/api/admin/tuition/condition")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<List<TuitionFindAllResponse>>findStudentTuitioncond(@Login LoginUser loginUser, TuitionSearchCondition condition) {
        return SuccessResponse.<List<TuitionFindAllResponse>>builder()
                .status(200)
                .data(tuitionService.findTuitions(loginUser,condition))
                .message("등록금 조회 성공")
                .build();

    }



}

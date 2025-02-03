package com.example.campushub.tuition.controller;


import com.example.campushub.global.common.SuccessResponse;
import com.example.campushub.global.security.Login;
import com.example.campushub.tuition.dto.TuitionFindAllResponse;
import com.example.campushub.tuition.dto.TuitionSearchCondition;
import com.example.campushub.tuition.dto.TuitionStudentResponse;
import com.example.campushub.tuition.service.TuitionService;
import com.example.campushub.user.dto.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TuitonController {

    private final TuitionService tuitionService;

    // //등록금 납부 관리 조회  @ModelAttribute TuitionSearchCondition condition
    // @GetMapping("/api/admin/tuition/condition")
    // @ResponseStatus(HttpStatus.OK)
    // public SuccessResponse<List<TuitionFindAllResponse>>findStudentTuitioncond(@Login LoginUser loginUser, TuitionSearchCondition condition) {
    //     return SuccessResponse.<List<TuitionFindAllResponse>>builder()
    //             .status(200)
    //             .data(tuitionService.findTuitions(loginUser,condition))
    //             .message("등록금 조회 성공")
    //             .build();
    //
    //
    // }
    //등록금 납부 신청 수락
    @PostMapping("/api/admin/tuition/condition/acceptstatus")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<Void> acceptPaymentStatus(@Login LoginUser loginUser, @RequestBody List<String> userNum) {
        tuitionService.updatePaymentStatus(loginUser,userNum);

        return SuccessResponse.<Void>builder()
                .status(200)
                .message("등록금 납부 신청 수락 완료")
                .build();
    }

    //(학생)본인 등록금 조회
    @GetMapping("/api/student/tuition")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<TuitionStudentResponse> getMyTuition(@Login LoginUser loginUser) {
        return SuccessResponse.<TuitionStudentResponse>builder()
                .status(200)
                .data(tuitionService.getStudentTuitionDetail(loginUser))
                .message("본인 등록금 조회 성공")
                .build();
    }

    //등록금 납부 신청
    @PostMapping("/api/student/tuition/{tuitionId}")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<Void> applyTuitionPayment(@Login LoginUser loginUser, @PathVariable Long tuitionId){
        tuitionService.applyTuitionPayment(loginUser,tuitionId);

        return SuccessResponse.<Void>builder()
                .status(200)
                .message("등록금 납부 신청 성공")
                .build();
    }






}

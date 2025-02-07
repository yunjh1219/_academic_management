package com.example.campushub.exam.controller;


import com.example.campushub.exam.dto.ExamEditDto;
import com.example.campushub.exam.dto.ExamFindAllResponse;

import com.example.campushub.exam.dto.ExamScoreInputRequest;
import com.example.campushub.exam.dto.ExamSearchCondition;
import com.example.campushub.exam.service.ExamService;
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
public class ExamController {

    private final ExamService examService;

        //강의별 학생 조회
    @GetMapping("/api/professor/exam/condition")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<List<ExamFindAllResponse>> findUserExamByCourse(@Login LoginUser loginUser,
        ExamSearchCondition cond) {

        return SuccessResponse.<List<ExamFindAllResponse>>builder()
                .status(200)
                .message("강의별 학생 조회 성공")
                .data(examService.getExamScores(loginUser,cond))
                .build();
    }
       //  강의별 학생 성적 기입
    @PostMapping("/api/professor/exam/{userCourseId}")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<Void> createExams(@Login LoginUser loginUser, @RequestBody @Valid ExamScoreInputRequest examRequest, @PathVariable Long userCourseId) {

        examService.updateExamScore(loginUser,examRequest, userCourseId);

        return SuccessResponse.<Void>builder()
                .status(200)
                .message("성적 기입 성공")
                .build();
    }

    //.todo 삭제
        //강의 별 학생 성적 수정
    // @PostMapping("/api/professor/exam/updateExam/{examId}")
    // @ResponseStatus(HttpStatus.OK)
    // public SuccessResponse<Void> updateExam(@RequestBody Long examId, @RequestBody ExamEditDto examEditDto) {
    //     examService.editExam(examId,examEditDto);
    //
    //     return SuccessResponse.<Void>builder()
    //             .status(200)
    //             .message("성적 수정 성공")
    //             .build();
    // }

}

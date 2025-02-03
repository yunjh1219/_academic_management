package com.example.campushub.scholarship.controller;

import com.example.campushub.global.common.SuccessResponse;
import com.example.campushub.global.security.Login;
import com.example.campushub.scholarship.dto.GetMyScholarshipDto;
import com.example.campushub.scholarship.dto.ScholarshipCreateDto;
import com.example.campushub.scholarship.dto.ScholarshipResponseDto;
import com.example.campushub.scholarship.dto.ScholarshipSearchCondition;
import com.example.campushub.scholarship.service.ScholarshipService;

import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.dto.UserFindOneSimpleDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScholarshipController {

    private final ScholarshipService scholarshipService;


    //장학금 조회 (관리자)
    @GetMapping("/api/admin/scholarship/condition")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<List<ScholarshipResponseDto>> findScholarships(@Login LoginUser loginUser,ScholarshipSearchCondition condition, Model model) {
        return SuccessResponse.<List<ScholarshipResponseDto>>builder()
                .status(200)
                .message("장학금 학생 조회 성공")
                .data(scholarshipService.findScholarships(condition, loginUser))
                .build();
    }

    //장학금 등록 (관리자)
    @PostMapping("/api/admin/scholarship")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<Void> signupScholarship(@Login LoginUser loginUser,
                                                   @RequestBody @Valid ScholarshipCreateDto createDto,
                                                   HttpServletRequest request) {




        scholarshipService.createScholarship(createDto,loginUser);

        return SuccessResponse.<Void>builder()
                .status(200)
                .message("장학금 등록 성공")
                .build();


    }

    //장학금 등록 학번페이지 (이름 ,학과 자동조회)
    @GetMapping("/api/admin/scholarship/userfind")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<UserFindOneSimpleDto> getUserInfo(String userNum){

        UserFindOneSimpleDto userInfo = scholarshipService.getUserSimpleInfo(userNum);

        return SuccessResponse.<UserFindOneSimpleDto>builder()
                .status(200)
                .message("이름 및 학과 자동 조회성공")
                .data(userInfo)
                .build();
    }

    //사용자 본인 장학금 조회
    @GetMapping("/api/student/scholarship")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<List<GetMyScholarshipDto>> getMyScholarships(@Login LoginUser loginUser){
        return SuccessResponse.<List<GetMyScholarshipDto>>builder()
                .status(200)
                .data(scholarshipService.findMyScholarships(loginUser))
                .message("사용자 장학금 조회 성공")
                .build();
    }



}

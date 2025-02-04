package com.example.campushub.notice.controller;

import com.example.campushub.global.common.SuccessResponse;
import com.example.campushub.global.security.Login;
import com.example.campushub.notice.dto.NoticeCreateDto;
import com.example.campushub.notice.dto.NoticeResponseDto;
import com.example.campushub.notice.dto.NoticeTypeSearchCondition;
import com.example.campushub.notice.service.NoticeService;
import com.example.campushub.user.dto.LoginUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NoticeController {

    final NoticeService noticeService;

    //공지생성
    @PostMapping("/api/notice/create")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<Void> createNotice(@Login LoginUser loginUser, @RequestBody @Valid NoticeCreateDto createDto){


        noticeService.createNotice(loginUser, createDto);

        return SuccessResponse.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("공지사항 생성 성공")
                .build();
    }

    //컨디션  공지 전체 조회
    //요청 type에 따라 학사 혹은 교수 공지 사항
    @GetMapping("/api/notice/all")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<List<NoticeResponseDto>> findAllByTypeCondition(@Login LoginUser loginUser, NoticeTypeSearchCondition cond) {

        return SuccessResponse.<List<NoticeResponseDto>>builder()
                .status(HttpStatus.OK.value())
                .message("공지 조회 성공")
                .data(noticeService.findAllByTypeCondition(loginUser, cond))
                .build();
    }

}

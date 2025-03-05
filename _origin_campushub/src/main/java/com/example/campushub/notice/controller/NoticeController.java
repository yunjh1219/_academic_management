package com.example.campushub.notice.controller;


import com.example.campushub.global.common.SuccessResponse;
import com.example.campushub.global.security.Login;
import com.example.campushub.notice.dto.NoticeCreateRequest;
import com.example.campushub.notice.dto.NoticeFindAllResponse;
import com.example.campushub.notice.dto.NoticeResponse;
import com.example.campushub.notice.dto.NoticeTypeSearchCondition;
import com.example.campushub.notice.service.NoticeService;
import com.example.campushub.user.dto.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    //공지생성
    @PostMapping("/api/notice/create")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<Void> createNotice(@Login LoginUser loginUser, @RequestBody @Valid NoticeCreateRequest createDto){


        noticeService.createNotice(loginUser, createDto);

        return SuccessResponse.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("공지사항 생성 성공")
                .build();
    }

    //컨디션 공지 전체 조회
    @GetMapping("/api/notice/condition")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<List<NoticeFindAllResponse>> findAllByTypeCondition(@Login LoginUser loginUser, NoticeTypeSearchCondition cond) {

        return SuccessResponse.<List<NoticeFindAllResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("공지 조회 성공")
                .data(noticeService.findAllByTypeCondition(loginUser, cond))
                .build();
    }

    //공지 단건 조회
    @GetMapping("/api/notice/condition/{noticeId}")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<NoticeResponse> getNotice(@Login LoginUser loginUser, @PathVariable Long noticeId) {
        return SuccessResponse.<NoticeResponse>builder()
                .status(200)
                .data(noticeService.findNotice(loginUser,noticeId))
                .message("공지 단건 조회 성공")
                .build();
    }

}

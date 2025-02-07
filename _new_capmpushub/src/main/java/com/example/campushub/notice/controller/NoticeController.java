package com.example.campushub.notice.controller;

import com.example.campushub.global.common.SuccessResponse;
import com.example.campushub.global.security.Login;
import com.example.campushub.notice.dto.*;
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

    //공지사항 생성
    @PostMapping("/api/admin/notice")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<Void> createNotice(@Login LoginUser loginUser, @RequestBody @Valid NoticeCreateRequestDto createDto) {

        noticeService.createNotice(loginUser, createDto);

        return SuccessResponse.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("공지 생성 성공")
                .build();
    }

    //공지사항 수정
    @PatchMapping("/api/admin/notice/{noticeId}")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<Void> editNotice(@Login LoginUser loginUser, @PathVariable Long noticeId
    , @RequestBody @Valid NoticeEditDto editDto) {

        noticeService.editNotice(loginUser, noticeId, editDto);

        return SuccessResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("공지 수정 성공")
                .build();
    }

    //공지사항 삭제
    @DeleteMapping("/api/admin/notice/{noticeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SuccessResponse<Void> deleteNotice(@Login LoginUser loginUser, @PathVariable Long noticeId) {

        noticeService.deleteNoticeByNoticeId(loginUser, noticeId);

        return SuccessResponse.<Void>builder()
                .status(200)
                .message("공지 삭제 성공")
                .build();
    }

    //공지사항 전체 조회 + 컨디션
    @GetMapping("/api/notice/condition")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<List<NoticeFindAllResponseDto>> findAllByNoticeCondition(@Login LoginUser loginUser, NoticeSearchCondition cond){

        return SuccessResponse.<List<NoticeFindAllResponseDto>>builder()
                .status(HttpStatus.OK.value())
                .message("공지 조회 성공")
                .data(noticeService.findAllByNoticeCondition(loginUser, cond))
                .build();
    }

    //공지사항 단건 조회
    @GetMapping("/api/notice/condition/{noticeId}")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<NoticeFindOneResponseDto> getNotice(@Login LoginUser loginUser, @PathVariable Long noticeId) {

        return SuccessResponse.<NoticeFindOneResponseDto>builder()
                .status(200)
                .data(noticeService.findNotice(loginUser,noticeId))
                .message("공지 단건 조회 성공")
                .build();
    }
}

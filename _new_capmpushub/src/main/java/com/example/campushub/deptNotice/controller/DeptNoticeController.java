package com.example.campushub.deptNotice.controller;

import com.example.campushub.deptNotice.dto.DeptNoticeCreateRequestDto;
import com.example.campushub.deptNotice.dto.DeptNoticeEditDto;
import com.example.campushub.deptNotice.dto.DeptNoticeFindAllResponseDto;
import com.example.campushub.deptNotice.dto.DeptNoticeSearchCondition;
import com.example.campushub.deptNotice.service.DeptNoticeService;
import com.example.campushub.global.common.SuccessResponse;
import com.example.campushub.global.security.Login;
import com.example.campushub.notice.service.NoticeService;
import com.example.campushub.user.dto.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DeptNoticeController {

    private final DeptNoticeService deptNoticeService;
    private final NoticeService noticeService;


    //학과 공지사항 생성
    @PostMapping("/api/admin/deptnotice")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<Void> createDeptNotice(@Login LoginUser loginUser, @RequestBody @Valid DeptNoticeCreateRequestDto createDto){

        deptNoticeService.createDeptNotice(loginUser, createDto);

        return SuccessResponse.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("학과 공지 생성 성공")
                .build();
    }

    //학과 공지사항 수정
    @PatchMapping("/api/admin/deptnotice/{deptNoticeId}")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<Void> editNotice(@Login LoginUser loginUser, @PathVariable Long deptNoticeId
            , @RequestBody @Valid DeptNoticeEditDto editDto) {

        deptNoticeService.editDeptNotice(loginUser, deptNoticeId, editDto);

        return SuccessResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("학과 공지 수정 성공")
                .build();
    }

    //학과 공지사항 삭제
    @DeleteMapping("/api/admin/deptnotice/{deptNoticeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SuccessResponse<Void> deletNotice(@Login LoginUser loginUser, @PathVariable Long deptNoticeId) {

        deptNoticeService.deletDeptNoticeByDeptNoticeId(loginUser, deptNoticeId);

        return SuccessResponse.<Void>builder()
                .status(200)
                .message("학과 공지 삭제 성공")
                .build();
    }

    //학과 공지사항 전체 조회 + 컨디션
    @GetMapping("/api/deptnotice/condition")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<List<DeptNoticeFindAllResponseDto>> findAllByDeptNoticeCondition(@Login LoginUser loginUser, DeptNoticeSearchCondition cond){

        return SuccessResponse.<List<DeptNoticeFindAllResponseDto>>builder()
                .status(HttpStatus.OK.value())
                .message("학과 공지 조회 성공")
                .data(deptNoticeService.findAllByDeptNoticeCondition(loginUser, cond))
                .build();
    }


    //학과 공지사항 단건 조회

}

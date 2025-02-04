package com.example.campushub.notice.controller;

import com.example.campushub.global.security.Login;
import com.example.campushub.notice.dto.NoticeCreateRequestDto;
import com.example.campushub.notice.dto.NoticeListAll;
import com.example.campushub.notice.dto.NoticeResponseDto;
import com.example.campushub.notice.dto.NoticeSearchCondition;
import com.example.campushub.notice.service.NoticeService;
import com.example.campushub.user.dto.LoginUser;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class NoticeController {

    private final NoticeService noticeService;
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 관리자가 공지사항 리스트 조회
    @GetMapping("/api/admin/notice")
    public ResponseEntity<List<NoticeListAll>> getAllNoticesByAdmin(
            @RequestParam(required = false, defaultValue = "title") String filter,
            @RequestParam(required = false) String keyword) {

        // 검색 조건 DTO 생성
        NoticeSearchCondition condition = NoticeSearchCondition.builder()
                .filter(filter)
                .keyword(keyword)
                .build();

        // 공지사항 전체 조회
        List<NoticeListAll> notices = noticeService.getAllNoticesByAdmin(condition);

        return ResponseEntity.ok(notices);
    }

    // 공지사항 리스트 조회
    @GetMapping("/api/notice")
    public ResponseEntity<Page<NoticeListAll>> getNotices(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "title") String filter, // 제목(default) 또는 작성자
            @RequestParam(required = false) String keyword) {

        // 검색 조건 DTO 생성
        NoticeSearchCondition condition = NoticeSearchCondition.builder()
                .filter(filter)
                .keyword(keyword)
                .build();

        // 페이징된 공지사항 리스트 가져오기
        Page<NoticeListAll> notices = noticeService.getNoticesByCondition(condition, page, size);

        return ResponseEntity.ok(notices);
    }

    // 공지사항 세부 조회
    @GetMapping("/api/notice/{id}")
    public ResponseEntity<NoticeResponseDto> getNoticeDetail(@PathVariable Long id) {
        NoticeResponseDto notice = noticeService.getNoticeDetail(id);
        return ResponseEntity.ok(notice);
    }

    // 공지사항 작성
    @PostMapping("/api/notice/write")
    public ResponseEntity<Void> createNotice(
            @RequestBody NoticeCreateRequestDto requestDto,
            @Login LoginUser loginUser) {

        // 공지사항 생성 요청 처리
        noticeService.createNotice(requestDto, loginUser);

        return ResponseEntity.ok().build();
    }

    // 공지사항 삭제
    @DeleteMapping("/api/notice/delete/{id}")
    public ResponseEntity<Void> deleteNotice(
            @PathVariable Long id,
            @Login LoginUser loginUser) {

        // 공지사항 삭제 호출
        noticeService.deleteNotice(id, loginUser);

        return ResponseEntity.noContent().build();
    }

    // 공지사항 수정 페이지 반환
    @GetMapping("/api/notice/edit/{id}")
    public ResponseEntity<NoticeResponseDto> getEditNotice(@PathVariable Long id) {
        // 기존 공지사항 데이터를 가져옴
        NoticeResponseDto notice = noticeService.getNoticeDetail(id);

        // JSON 형식으로 데이터 반환
        return ResponseEntity.ok(notice);
    }

    // 공지사항 수정
    @PutMapping("/api/notice/update/{id}")
    public ResponseEntity<Void> updateNotice(
            @PathVariable Long id,
            @RequestBody NoticeCreateRequestDto requestDto,
            @Login LoginUser loginUser) {

        // 수정 요청 처리
        noticeService.updateNotice(id, requestDto, loginUser);

        return ResponseEntity.ok().build();
    }


}

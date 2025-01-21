package com.example.campushub.notice.controller;

import com.example.campushub.notice.dto.NoticeCreateRequestDto;
import com.example.campushub.notice.dto.NoticeListAll;
import com.example.campushub.notice.dto.NoticeResponseDto;
import com.example.campushub.notice.dto.NoticeSearchCondition;
import com.example.campushub.notice.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 공지사항 리스트 조회
    @GetMapping("/notice")
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
    @GetMapping("/notice/{id}")
    public ResponseEntity<NoticeResponseDto> getNoticeDetail(@PathVariable Long id) {
        NoticeResponseDto notice = noticeService.getNoticeDetail(id);
        return ResponseEntity.ok(notice);
    }

    // 공지사항 작성
    @PostMapping("/notice/write")
    public ResponseEntity<Void> createNotice(
            @RequestBody NoticeCreateRequestDto requestDto,
            HttpSession session) {

        // 세션에서 유저 ID 가져오기
        Long userId = (Long) session.getAttribute("userId");

        // 공지사항 생성 요청 처리
        noticeService.createNotice(requestDto, userId);

        return ResponseEntity.ok().build();
    }

    // 공지사항 삭제
    @DeleteMapping("/notice/{id}")
    public ResponseEntity<Void> deleteNotice(
            @PathVariable Long id,
            HttpSession session) {

        // 세션에서 로그인한 사용자 타입 가져오기
        String userType = (String) session.getAttribute("userType");

        // 공지사항 삭제 호출
        noticeService.deleteNotice(id, userType);

        return ResponseEntity.noContent().build();
    }

    // 공지사항 수정 페이지 반환
    @GetMapping("/notice/edit/{id}")
    public String editNotice(@PathVariable Long id, Model model) {
        // 기존 공지사항 데이터 가져오기
        NoticeResponseDto notice = noticeService.getNoticeDetail(id);
        model.addAttribute("notice", notice);

        return "notice/editForm"; // 수정 페이지 뷰 반환
    }

    // 공지사항 수정
    @PutMapping("/notice/{id}")
    public ResponseEntity<Void> updateNotice(
            @PathVariable Long id,
            @RequestBody NoticeCreateRequestDto requestDto,
            HttpSession session) {

        // 세션에서 유저 ID 가져오기
        Long userId = (Long) session.getAttribute("userId");

        // 수정 요청 처리
        noticeService.updateNotice(id, requestDto, userId);

        return ResponseEntity.ok().build();
    }


}

package com.home._front.controller.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin-potal/notices")
public class NoticeController {


    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;

    @GetMapping
    @ResponseBody
    public List<Notice> showNotices() {
        // notice 테이블에서 모든 공지사항 데이터 조회
        return noticeRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Notice> getNoticeById(@PathVariable Long id) {
        Notice notice = noticeService.findById(id);
        if (notice != null) {
            return ResponseEntity.ok(notice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateNotice(@PathVariable Long id, @RequestBody NoticeDto noticeDto) {
        try {
            noticeService.updateNotice(id, noticeDto);
            return ResponseEntity.ok("공지사항이 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("공지사항 업데이트 중 오류 발생");
        }
    }


    @PostMapping
    public ResponseEntity<String> createNotice(@RequestBody NoticeDto noticeDto) {
        try {
            // 새로운 공지사항을 저장하는 서비스 메서드 호출
            noticeService.createNotice(noticeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("새로운 공지사항이 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("공지사항 저장 중 오류 발생");
        }
    }

}

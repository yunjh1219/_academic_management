package com.home._front.controller.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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



}

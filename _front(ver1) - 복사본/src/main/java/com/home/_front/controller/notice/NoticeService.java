package com.home._front.controller.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {


    private final NoticeRepository noticeRepository;



    public Notice findById(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 공지사항이 없습니다."));
    }

    @Transactional
    public void updateNotice(Long id, NoticeDto noticeDto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항을 찾을 수 없습니다."));

        notice.setTitle(noticeDto.getTitle());
        notice.setContent(noticeDto.getContent());
        notice.setAuthor(noticeDto.getAuthor());
        notice.setUpdatedDate(LocalDateTime.now());

        noticeRepository.save(notice);  // JPA가 변경사항을 자동으로 감지하여 업데이트함
    }

}

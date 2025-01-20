package com.example.campushub.notice.repository;

import com.example.campushub.notice.dto.NoticeListAll;
import com.example.campushub.notice.dto.NoticeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeRepositoryCustom {

    // 공지사항 전체 조회
    Page<NoticeListAll> findAllByCondition(String title, String createdBy, Pageable pageable);

    // 공지사항 세부 조회
    NoticeResponseDto findNoticeDetailById(Long id);

}
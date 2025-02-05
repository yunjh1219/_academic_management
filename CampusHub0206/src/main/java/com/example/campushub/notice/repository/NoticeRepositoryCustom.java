package com.example.campushub.notice.repository;

import com.example.campushub.notice.dto.NoticeResponseDto;
import com.example.campushub.notice.dto.NoticeTypeSearchCondition;

import java.util.List;

public interface NoticeRepositoryCustom {

    List<NoticeResponseDto> findAllByTypeCondition(NoticeTypeSearchCondition condition);
}

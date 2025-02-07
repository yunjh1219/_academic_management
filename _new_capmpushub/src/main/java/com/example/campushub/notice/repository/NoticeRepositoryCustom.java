package com.example.campushub.notice.repository;

import com.example.campushub.notice.dto.NoticeFindAllResponseDto;
import com.example.campushub.notice.dto.NoticeFindOneResponseDto;
import com.example.campushub.notice.dto.NoticeSearchCondition;

import java.util.List;
import java.util.Optional;

public interface NoticeRepositoryCustom {

    List<NoticeFindAllResponseDto> findAllByNoticeCondition(NoticeSearchCondition condition);
    Optional<NoticeFindOneResponseDto> getNoticeById(Long Id);
}

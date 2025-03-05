package com.example.campushub.notice.repository;

import com.example.campushub.notice.dto.NoticeFindAllResponse;
import com.example.campushub.notice.dto.NoticeResponse;
import com.example.campushub.notice.dto.NoticeTypeSearchCondition;

import java.util.List;
import java.util.Optional;

public interface NoticeRepositoryCustom {

    List<NoticeFindAllResponse> findAllByTypeCondition(NoticeTypeSearchCondition condition);
    Optional<NoticeResponse> getNoticeById(Long id);

}

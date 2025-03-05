package com.example.campushub.notice.repository;

import com.example.campushub.notice.domain.NoticeType;
import com.example.campushub.notice.dto.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.campushub.notice.domain.QNotice.notice;
import static com.example.campushub.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<NoticeFindAllResponse> findAllByTypeCondition(NoticeTypeSearchCondition condition){
        return queryFactory.select(new QNoticeFindAllResponse(
                        notice.id,
                        notice.noticeType,
                        notice.noticeTitle,
                        notice.noticeContent,
                        notice.createdAt,
                        notice.updatedAt,
                        notice.user.userName
                ))
                .from(notice)  // `notice`는 엔티티의 인스턴스, 즉 공지사항 테이블을 나타냅니다.
                .leftJoin(notice.user, user)
                .where(noticeTypeEq(condition.getNoticeType()))  // 조건에 맞는 필터링
                .fetch(); // 쿼리 실행 후 결과 반환
    }

    @Override
    public Optional<NoticeResponse> getNoticeById(Long id){
        NoticeResponse fetchOne = queryFactory.select(new QNoticeResponse(
                notice.id,
                notice.noticeType,
                notice.noticeTitle,
                notice.noticeContent,
                notice.createdAt,
                notice.updatedAt,
                notice.user.userName
        ))
                .from(notice)
                .where(notice.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(fetchOne);
    }

    private BooleanExpression noticeTypeEq(String noticeType) {
        return noticeType == null ? null : notice.noticeType.eq(NoticeType.of(noticeType));

    }

}
package com.example.campushub.notice.repository;

import com.example.campushub.notice.domain.NoticeType;
import com.example.campushub.notice.dto.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.campushub.deptNotice.domain.QDeptNotice.deptNotice;
import static com.example.campushub.notice.domain.QNotice.notice;
import static com.example.campushub.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<NoticeFindAllResponseDto> findAllByNoticeCondition(NoticeSearchCondition condition){
        return queryFactory.select(new QNoticeFindAllResponseDto(
                notice.id,
                notice.noticeType,
                user.userName,
                notice.noticeTitle,
                notice.createdAt
        ))
                .from(notice)
                .join(user).on(notice.user.eq(user)) // Course와 User를 JOIN
                .where(
                        noticeTypeEq(condition.getNoticeType()),
                        noticeTitleEq(condition.getNoticeTitle()),
                        startDateEq(condition.getStartDate()), // 시작 날짜 조건 추가
                        endDateEq(condition.getEndDate()) // 종료 날짜 조건 추가
                )
                .fetch();
    }

    @Override
    public Optional<NoticeFindOneResponseDto> getNoticeById(Long id){
        NoticeFindOneResponseDto fetchOne = queryFactory.select(new QNoticeFindOneResponseDto(
                        notice.id,
                        notice.noticeType,
                        user.userName,
                        notice.noticeTitle,
                        notice.noticeContent,
                        notice.createdAt,
                        notice.updatedAt
                ))
                .from(notice)
                .join(user).on(notice.user.eq(user)) // Course와 User를 JOIN
                .where(notice.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(fetchOne);
    }

    private BooleanExpression noticeTypeEq(String noticeType) {
        return noticeType == null ? null : notice.noticeType.eq(NoticeType.of(noticeType));
    }
    private BooleanExpression noticeTitleEq(String noticeTitle) {
        return noticeTitle == null ? null : notice.noticeTitle.contains(noticeTitle);  // 제목에 포함되는 값으로 비교
    }

    // 시작 날짜 조건
    private BooleanExpression startDateEq(LocalDateTime startDate) {
        return startDate == null ? null : notice.createdAt.goe(startDate); // 시작 날짜보다 크거나 같은 경우
    }

    // 종료 날짜 조건
    private BooleanExpression endDateEq(LocalDateTime endDate) {
        return endDate == null ? null : notice.createdAt.loe(endDate); // 종료 날짜보다 작거나 같은 경우
    }
}

package com.example.campushub.notice.repository;

import com.example.campushub.notice.domain.QNotice;
import com.example.campushub.notice.dto.*;
import com.example.campushub.user.domain.QUser;
import com.example.campushub.user.domain.Role;
import com.example.campushub.user.domain.Type;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 공지사항 전체 조회 (페이징 포함)
    @Override
    public Page<NoticeListAll> findAllByCondition(String title, String createdBy, Pageable pageable) {
        QNotice notice = QNotice.notice;
        QUser user = QUser.user;

        // 메인 데이터 쿼리
        List<NoticeListAll> content = queryFactory.select(new QNoticeListAll(
                        notice.id,
                        notice.title,
                        notice.createdAt,
                        user.id,
                        user.userName
                ))
                .from(notice)
                .leftJoin(notice.user, user)
                .where(
                        titleContains(title),
                        createdByEq(createdBy),
                        user.role.eq(Role.ADMIN) // user.type 필터링
                )
                .offset(pageable.getOffset()) // 페이징 시작점
                .limit(pageable.getPageSize()) // 페이지 크기
                .fetch();

        // 전체 개수 쿼리
        long count = Optional.ofNullable(queryFactory
                .select(notice.count())
                .from(notice)
                .leftJoin(notice.user, user)
                .where(
                        titleContains(title),
                        createdByEq(createdBy),
                        user.role.eq(Role.ADMIN) // user.type 필터링
                )
                .fetchOne()).orElse(0L);

        // Page 객체 반환
        return PageableExecutionUtils.getPage(content, pageable, () -> count);
    }

    // 공지사항 세부 조회
    @Override
    public NoticeResponseDto findNoticeDetailById(Long id) {
        QNotice notice = QNotice.notice;
        QUser user = QUser.user;

        return queryFactory.select(new QNoticeResponseDto(
                        notice.id,
                        notice.title,
                        notice.content,
                        notice.createdAt,
                        user.userName // 작성자 이름
                ))
                .from(notice)
                .leftJoin(notice.user, user)
                .where(notice.id.eq(id)) // ID 조건
                .fetchOne(); // 단일 결과 반환
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? QNotice.notice.title.containsIgnoreCase(title) : null;
    }

    private BooleanExpression createdByEq(String createdBy) {
        return createdBy != null ? QNotice.notice.user.userName.eq(createdBy) : null;
    }

}

package com.example.campushub.deptNotice.repository;

import com.example.campushub.deptNotice.domain.DeptNoticeType;
import com.example.campushub.deptNotice.dto.DeptNoticeFindAllResponseDto;
import com.example.campushub.deptNotice.dto.DeptNoticeSearchCondition;
import com.example.campushub.deptNotice.dto.QDeptNoticeFindAllResponseDto;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.campushub.dept.domain.QDept.dept;
import static com.example.campushub.deptNotice.domain.QDeptNotice.deptNotice;
import static com.example.campushub.user.domain.QUser.user;


@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeptNoticeRepositoryCustomImpl implements DeptNoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<DeptNoticeFindAllResponseDto> findAllByDeptNoticeCondition(DeptNoticeSearchCondition condition){
        return  queryFactory.select(new QDeptNoticeFindAllResponseDto(
                deptNotice.id,
                deptNotice.deptNoticeType,
                dept.deptName,
                user.userName,
                deptNotice.noticeTitle,
                deptNotice.createdAt
        ))
                .from(deptNotice)
                .join(deptNotice.dept, dept)  // deptNotice와 dept를 조인
                .join(deptNotice.user, user)  // deptNotice와 user를 조인
                .where(
                        deptNoticeTypeEq(condition.getDeptNoticeType()),
                        deptNameEq(condition.getDeptName()),
                        noticeTitleEq(condition.getNoticeTitle()),
                        startDateEq(condition.getStartDate()), // 시작 날짜 조건 추가
                        endDateEq(condition.getEndDate()) // 종료 날짜 조건 추가
                )  // 조건을 추가
                .fetch();  // 결과 반환
    }

    // 조건 메서드들 수정
    private BooleanExpression deptNoticeTypeEq(String deptNoticeType) {
        return deptNoticeType == null ? null : deptNotice.deptNoticeType.eq(DeptNoticeType.valueOf(deptNoticeType));
    }

    private BooleanExpression deptNameEq(String deptName) {
        return deptName == null ? null : dept.deptName.eq(deptName);  // deptName을 비교
    }

    private BooleanExpression noticeTitleEq(String noticeTitle) {
        return noticeTitle == null ? null : deptNotice.noticeTitle.contains(noticeTitle);  // 제목에 포함되는 값으로 비교
    }

    // 시작 날짜 조건
    private BooleanExpression startDateEq(LocalDateTime startDate) {
        return startDate == null ? null : deptNotice.createdAt.goe(startDate); // 시작 날짜보다 크거나 같은 경우
    }

    // 종료 날짜 조건
    private BooleanExpression endDateEq(LocalDateTime endDate) {
        return endDate == null ? null : deptNotice.createdAt.loe(endDate); // 종료 날짜보다 작거나 같은 경우
    }
}
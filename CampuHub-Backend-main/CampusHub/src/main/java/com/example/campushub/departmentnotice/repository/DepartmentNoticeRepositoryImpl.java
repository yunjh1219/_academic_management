package com.example.campushub.departmentnotice.repository;

import com.example.campushub.departmentnotice.domain.DepartmentNotice;
import com.example.campushub.departmentnotice.domain.QDepartmentNotice;
import com.example.campushub.departmentnotice.dto.DepartmentNoticeResponseDto;
import com.example.campushub.departmentnotice.dto.QDepartmentNoticeResponseDto;
import com.example.campushub.departmentnotice.dto.DepartmentNoticeSearchCondition;
import com.example.campushub.user.domain.QUser;
import com.example.campushub.user.domain.Type;
import com.example.campushub.usercourse.domain.QUserCourse;
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
public class DepartmentNoticeRepositoryImpl implements DepartmentNoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 학과 공지사항 리스트 조회
    @Override
    public Page<DepartmentNoticeResponseDto> findDepartmentNoticesForUser(String userNum, DepartmentNoticeSearchCondition condition, Pageable pageable) {
        QDepartmentNotice departmentNotice = QDepartmentNotice.departmentNotice;
        QUser user = QUser.user;
        QUserCourse userCourse = QUserCourse.userCourse;

        // 메인 쿼리: 사용자가 등록 또는 수강한 강의의 공지사항만 조회
        List<DepartmentNoticeResponseDto> content = queryFactory
                .select(new QDepartmentNoticeResponseDto(
                        departmentNotice.id,
                        departmentNotice.title,
                        departmentNotice.content,
                        departmentNotice.author.userName,
                        userCourse.course.courseName,
                        departmentNotice.createdAt
                ))
                .from(departmentNotice)
                .join(departmentNotice.author, user) // 공지사항 작성자
                .join(departmentNotice.userCourse, userCourse) // 공지사항과 관련된 강의
                .where(
                        userCourse.user.userNum.eq(userNum), // 사용자가 등록된 강의만 조회
                        applyFilter(condition.getFilter(), condition.getKeyword()) // 필터 조건 적용
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 쿼리
        long total = queryFactory
                .select(departmentNotice.count())
                .from(departmentNotice)
                .join(departmentNotice.userCourse, userCourse)
                .where(
                        userCourse.user.userNum.eq(userNum), // 사용자가 등록된 강의만 조회
                        applyFilter(condition.getFilter(), condition.getKeyword()) // 필터 조건 적용
                )
                .fetchOne();

        return PageableExecutionUtils.getPage(content, pageable, () -> total);
    }

    // 학과 공지사항 세부 조회
    @Override
    public Optional<DepartmentNoticeResponseDto> findByIdAndUser(Long id, String userNum) {
        QDepartmentNotice departmentNotice = QDepartmentNotice.departmentNotice;
        QUserCourse userCourse = QUserCourse.userCourse;
        QUser user = QUser.user;

        DepartmentNoticeResponseDto notice = queryFactory
                .select(new QDepartmentNoticeResponseDto(
                        departmentNotice.id,
                        departmentNotice.title,
                        departmentNotice.content,
                        user.userName,
                        userCourse.course.courseName,
                        departmentNotice.createdAt
                ))
                .from(departmentNotice)
                .join(departmentNotice.author, user)
                .leftJoin(departmentNotice.userCourse, userCourse)
                .where(
                        departmentNotice.id.eq(id),
                        user.userNum.eq(userNum)
                                .or(userCourse.user.userNum.eq(userNum))
                )
                .fetchOne();

        return Optional.ofNullable(notice);
    }

    private BooleanExpression applyFilter(String filter, String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }

        QDepartmentNotice departmentNotice = QDepartmentNotice.departmentNotice;
        QUser user = QUser.user;
        QUserCourse userCourse = QUserCourse.userCourse;

        return switch (filter) {
            case "title" -> departmentNotice.title.containsIgnoreCase(keyword); // 제목 필터
            case "createdBy" -> user.userName.containsIgnoreCase(keyword); // 작성자 필터
            case "courseName" -> userCourse.course.courseName.containsIgnoreCase(keyword); // 강의명 필터
            default -> null;
        };
    }
}

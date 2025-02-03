package com.example.campushub.attendance.repository;

import com.example.campushub.attendance.dto.*;
import com.example.campushub.nweek.domain.Week;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.campushub.attendance.domain.QAttendance.attendance;
import static com.example.campushub.course.domain.QCourse.course;
import static com.example.campushub.nweek.domain.QNWeek.nWeek;
import static com.example.campushub.user.domain.QUser.user;
import static com.example.campushub.usercourse.domain.QUserCourse.userCourse;


@Repository
@RequiredArgsConstructor
public class AttendanceRepositoryImpl implements AttendanceRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    //교수가 출석 조회
    @Override
    public List<AttendanceResponseDto> findAllByCondition(AttendanceSearchCondition cond) {
        return queryFactory.select(new QAttendanceResponseDto(
                user.userName,
                user.userNum,
                attendance.status
            ))
                .from(attendance)
                .leftJoin(attendance.userCourse, userCourse)
                .join(userCourse.user,user)
                .join(userCourse.course,course)
                .join(attendance.nWeek,nWeek)
                .join(nWeek.course,course)
                .where(userCourse.course.courseName.eq(cond.getCourseName()),
                        nWeek.week.eq(Week.valueOf(cond.getWeek())), nWeek.course.courseName.eq(cond.getCourseName()))
                .fetch();
    }

    //출석통계
    @Override
    public List<AttendanceSummaryDto> findAttendanceByCondition(AttendanceSearchCourseCondition cond) {
        return queryFactory
            .select(Projections.constructor(AttendanceSummaryDto.class,
                user.userName,
                user.userNum,
                getWeekStatus(Week.FIRST),
                getWeekStatus(Week.SECOND),
                getWeekStatus(Week.THIRD),
                getWeekStatus(Week.FOURTH),
                getWeekStatus(Week.FIFTH),
                getWeekStatus(Week.SIXTH),
                getWeekStatus(Week.SEVENTH),
                getWeekStatus(Week.EIGHTH),
                getWeekStatus(Week.NINTH),
                getWeekStatus(Week.TENTH),
                getWeekStatus(Week.ELEVENTH),
                getWeekStatus(Week.TWELFTH),
                getWeekStatus(Week.THIRTEENTH),
                getWeekStatus(Week.FOURTEENTH),
                getWeekStatus(Week.FIFTEENTH),
                getWeekStatus(Week.SIXTEENTH)
            ))
            .from(attendance)
            .leftJoin(attendance.userCourse, userCourse)
            .join(userCourse.user, user)
            .join(attendance.nWeek, nWeek)
            .join(nWeek.course, course)
            .where(course.courseName.eq(cond.getCourseName()),
                nWeek.course.courseName.eq(cond.getCourseName()))
            .groupBy(user.userName, user.userNum)
            .fetch();
    }

    //학생이 출석 조회
    @Override
    public List<AttendanceUserDto> findUserAttendance(AttendanceSearchCondition atten, String userNum) {
        return queryFactory.select(new QAttendanceUserDto(
                nWeek.week,
                attendance.status
        )).from(attendance)
                .leftJoin(attendance.userCourse,userCourse)
                .join(userCourse.user,user)
                .join(userCourse.course,course)
                .join(attendance.nWeek,nWeek)
                .where(userCourse.course.courseName.eq(atten.getCourseName()),
                        userCourse.user.userNum.eq(userNum))
                .orderBy(nWeek.week.desc())
                .fetch();
    }


    private BooleanExpression eqCourseName(String courseName) {
        return courseName == null ? null : course.courseName.eq(courseName);
    }

    private Expression<String> getWeekStatus(Week week) {
        return new CaseBuilder()
            .when(nWeek.week.eq(week)).then(attendance.status.stringValue())
            .otherwise("-"); // 기본값
    }

}

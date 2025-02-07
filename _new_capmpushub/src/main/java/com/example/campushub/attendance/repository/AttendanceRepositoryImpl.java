package com.example.campushub.attendance.repository;

import com.example.campushub.attendance.domain.Attendance;
import com.example.campushub.attendance.domain.QAttendance;
import com.example.campushub.attendance.dto.*;
import com.example.campushub.course.domain.QCourse;
import com.example.campushub.nweek.domain.NWeek;
import com.example.campushub.nweek.domain.Week;
import com.example.campushub.usercourse.domain.UserCourse;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

        QCourse course1 = new QCourse("course1");  // 첫 번째 course 별칭
        QCourse course2 = new QCourse("course2");  // 두 번째 course 별칭

        return queryFactory.select(new QAttendanceResponseDto(
                        user.userName,
                        user.userNum,
                        attendance.status
                ))
                .from(attendance)
                .leftJoin(attendance.userCourse, userCourse)
                .join(userCourse.user, user)
                .join(userCourse.course, course1) // 첫 번째 course 테이블
                .join(attendance.nWeek, nWeek)
                .join(nWeek.course, course2) // 두 번째 course 테이블
                .where(userCourse.course.courseName.eq(cond.getCourseName()),
                        nWeek.week.eq(Week.of(cond.getWeek())), nWeek.course.courseName.eq(cond.getCourseName()))
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
    public List<AttendanceUserDto> findUserAttendance(String coursename, String userNum) {

        QCourse course1 = new QCourse("course1");
        QCourse course2 = new QCourse("course2");

        return queryFactory.select(new QAttendanceUserDto(
                nWeek.week,
                attendance.status
        )).from(attendance)
                .leftJoin(attendance.userCourse,userCourse)
                .join(attendance.nWeek,nWeek)
                .join(userCourse.user,user)
                .join(userCourse.course,course1)
                .join(nWeek.course,course2)
                .where(userCourse.course.courseName.eq(coursename),
                        userCourse.user.userNum.eq(userNum),
                        nWeek.course.courseName.eq(coursename))
                .fetch();
    }

    @Override
    public Optional<Attendance> findByNWeekAndUserCourse(NWeek nWeek, UserCourse userCourse) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(attendance)
                        .where(
                                attendance.nWeek.eq(nWeek),
                                attendance.userCourse.eq(userCourse)
                        )
                        .fetchOne()
        );
    }



    private BooleanExpression eqUserCourseName(String courseName) {
        return courseName == null ? null : userCourse.course.courseName.eq(courseName);
    }
    private BooleanExpression eqNweekCourseName(String courseName) {
        return courseName == null ? null : nWeek.course.courseName.eq(courseName);
    }

    private Expression<String> getWeekStatus(Week week) {
        return new CaseBuilder()
            .when(nWeek.week.eq(week)).then(attendance.status.stringValue())
            .otherwise("-"); // 기본값
    }

}

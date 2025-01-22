package com.example.campushub.attendance.repository;

import com.example.campushub.attendance.dto.*;
import com.querydsl.core.types.dsl.BooleanExpression;
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

    @Override
    public List<AttendanceResponseDto> findAllByCondition(AttendanceSearchCondition atten) {
        return queryFactory.select(new QAttendanceResponseDto(
                user.userName,
                user.userNum,
                attendance.status
        )).from(attendance)
                .leftJoin(attendance.userCourse, userCourse)
                .join(userCourse.user,user)
                .join(userCourse.course,course)
                .join(attendance.nWeek,nWeek)
                .where(userCourse.course.courseName.eq(atten.getCourseName()),
                        attendance.nWeek.week.eq(atten.getWeek()))
                .fetch();
    }

    @Override
    public List<AllAttendanceResponseDto> findCourseByCondition(AttendanceSearchCourseCondition cond) {
        return queryFactory.select(new QAllAttendanceResponseDto(
                user.userName,
                user.userNum,
                attendance.status,
                attendance.nWeek.week
        )).from(attendance)
                .leftJoin(attendance.userCourse,userCourse)
                .join(userCourse.user,user)
                .join(userCourse.course,course)
                .join(attendance.nWeek,nWeek)
                .where(eqCourseName(cond.getCourseName()))
                .fetch();
    }

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

}

package com.example.campushub.course.repository;

import static com.example.campushub.course.domain.QCourse.*;
import static com.example.campushub.dept.domain.QDept.*;
import static com.example.campushub.user.domain.QUser.*;
import static com.example.campushub.usercourse.domain.QUserCourse.*;

import java.util.List;

import com.example.campushub.course.dto.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.campushub.course.domain.CourseDay;
import com.example.campushub.course.domain.CourseDivision;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseRepositoryCustomImpl implements CourseRepositoryCustom {

	private final JPAQueryFactory queryFactory;


	//교수 강의 전체+ 컨디션 조회
	@Override
	public List<CourseResponseDto> findAllByProfCondition(ProfCourseSearchCondition condition) {
		return queryFactory.select(new QCourseResponseDto(
			course.id,
			course.courseGrade,
			course.courseName,
			course.division,
			course.creditScore,
			user.userName,
			course.room,
			course.courseDay,
			course.startPeriod,
			course.endPeriod
		))
			.from(course)
			.join(user).on(course.user.eq(user))
			.where(courseDayEq(CourseDay.valueOf(condition.getCourseDay())),
				courseRoomEq(condition.getRoom()))
			.fetch();
	}

	//학생 강의 전체 + 컨디션 조회
	@Override
	public List<CourseResponseDto> findAllByStudCondition(StudCourseSearchCondition cond) {
		return queryFactory.select(new QCourseResponseDto(
			course.id,
			course.courseGrade,
			course.courseName,
			course.division,
			course.creditScore,
			user.userName,
			course.room,
			course.courseDay,
			course.startPeriod,
			course.endPeriod
		))
			.from(course)
			.join(user).on(course.user.eq(user))
			.join(dept).on(user.dept.eq(dept))
			.where(divisionEq(CourseDivision.valueOf(cond.getDivision())),
				deptNameEq(cond.getDeptName()),
				courseNameEq(cond.getCourseName()))
			.fetch();
	}

	//교수 본인 강의 조회
	@Override
	public List<CourseResponseDto> findAllByProf(String profNum) {
		return queryFactory.select(new QCourseResponseDto(
			course.id,
			course.courseGrade,
			course.courseName,
			course.division,
			course.creditScore,
			user.userName,
			course.room,
			course.courseDay,
			course.startPeriod,
			course.endPeriod
		))
			.from(course)
			.join(user).on(course.user.eq(user))
			.where(user.userNum.eq(profNum))
			.fetch();
	}

	//학생 본인 강의 조회
	@Observed
	public List<CourseResponseDto> findAllByStud(String studNum) {
		return queryFactory.select(new QCourseResponseDto(
			course.id,
			course.courseGrade,
			course.courseName,
			course.division,
			course.creditScore,
			user.userName,
			course.room,
			course.courseDay,
			course.startPeriod,
			course.endPeriod
		))
			.from(userCourse)
			.join(user).on(userCourse.user.eq(user))
			.join(course).on(userCourse.course.eq(course))
			.where(user.userNum.eq(studNum))
			.fetch();

	}
		//학생 본인 시간표 조회
	@Override
	public List<CourseCalenderDto> findCalByStud(String userNum) {
		return queryFactory.select(new QCourseCalenderDto(
				course.courseName,
				course.courseDay,
				course.startPeriod,
				course.endPeriod
		))
				.from(userCourse)
				.join(userCourse.course,course)
				.join(userCourse.user,user)
				.where(user.userNum.eq(userNum))
				.fetch();
	}

	//교수 본인 시간표 조회
	@Override
	public List<CourseCalenderDto> findCalByProf(String userNum) {
		return queryFactory.select(new QCourseCalenderDto(
				course.courseName,
				course.courseDay,
				course.startPeriod,
				course.endPeriod
		))
				.from(course)
				.join(course.user,user)
				.where(user.userNum.eq(userNum))
				.fetch();
	}


	//강의 중복 조건
	@Override
	public boolean existsByRoomAndTime(CourseCreateDto createDto) {
		return queryFactory
			.selectOne()
			.from(course)
			.where(isSameRoomCondition(createDto.getRoom(), CourseDay.valueOf(createDto.getCourseDay()), createDto.getStartPeriod(), createDto.getEndPeriod()))
			.fetchFirst() != null; // 데이터가 존재하면 true 반환
	}


	// 시간 범위가 겹치는 조건
	private BooleanExpression isTimeOverlapping(int startPeriod, int endPeriod) {
		return course.startPeriod.loe(endPeriod) // 새로운 시작 교시 <= 기존 끝 교시
			.and(course.endPeriod.goe(startPeriod)); // 새로운 끝 교시 >= 기존 시작 교시
	}
	// // 강의명이 겹치는 조건
	// private BooleanExpression isSameCourseCondition(String name, CourseDay courseDay, int startPeriod, int endPeriod) {
	// 	return course.courseName.eq(name)
	// 		.and(course.courseDay.eq(courseDay))
	// 		.and(isTimeOverlapping(startPeriod, endPeriod));
	// }
	// 강의실, 요일, 시간이 겹치는 조건
	private BooleanExpression isSameRoomCondition(String room, CourseDay courseDay, int startPeriod, int endPeriod) {
		return course.room.eq(room)
			.and(course.courseDay.eq(courseDay))
			.and(isTimeOverlapping(startPeriod, endPeriod));
	}
	private BooleanExpression courseDayEq(CourseDay courseDay) {
		return courseDay == null ? null : course.courseDay.eq(courseDay);
	}
	private BooleanExpression courseRoomEq(String room) {
		return room == null ? null : course.room.eq(room);
	}
	private BooleanExpression divisionEq(CourseDivision division) {
		return division == null ? null : course.division.eq(division);
	}
	private BooleanExpression deptNameEq(String deptName) {
		return deptName == null ? null : course.user.dept.deptName.eq(deptName);
	}
	private BooleanExpression courseNameEq(String courseName) {
		return courseName == null ? null : course.courseName.eq(courseName);
	}

}

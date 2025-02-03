package com.example.campushub.assignment.repository;

import static com.example.campushub.assignment.domain.QAssignment.*;
import static com.example.campushub.course.domain.QCourse.*;
import static com.example.campushub.nweek.domain.QNWeek.*;
import static com.example.campushub.user.domain.QUser.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.campushub.assignment.dto.AssignmentFindAllResponse;
import com.example.campushub.assignment.dto.AssignmentResponse;
import com.example.campushub.assignment.dto.AssignmentSearchCondition;
import com.example.campushub.assignment.dto.QAssignmentFindAllResponse;
import com.example.campushub.assignment.dto.QAssignmentResponse;
import com.example.campushub.course.domain.Course;
import com.example.campushub.course.domain.QCourse;
import com.example.campushub.nweek.domain.Week;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssignmentRepositoryCustomImpl implements AssignmentRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	//과제 전체 + 컨디션 조회 (학생)
	@Override
	public List<AssignmentFindAllResponse> findAllAssigmentByCond(AssignmentSearchCondition condition, List<String> courseNames) {
		return queryFactory.select(new QAssignmentFindAllResponse(
				assignment.id,
				nWeek.week,
				course.courseName,
				user.userName,
				assignment.limitDate,
				assignment.createDate
			))
			.from(assignment)
			.join(nWeek).on(assignment.nWeek.eq(nWeek))
			.join(course).on(nWeek.course.eq(course))
			.join(user).on(course.user.eq(user))
			.where(dynamicWhereCondition(condition, courseNames))
			.fetch();
	}

	@Override
	public Optional<AssignmentResponse> getAssignmentById(Long id) {
		AssignmentResponse fetchOne = queryFactory.select(new QAssignmentResponse(
			assignment.id,
			course.courseName,
			user.userName,
			nWeek.week,
			assignment.createDate,
			assignment.assignExplain,
			assignment.limitDate
		))
			.from(assignment)
			.join(nWeek).on(assignment.nWeek.eq(nWeek))
			.join(course).on(nWeek.course.eq(course))
			.join(user).on(course.user.eq(user))
			.where(assignment.id.eq(id))
			.fetchOne();

		return Optional.ofNullable(fetchOne);
	}



	private BooleanExpression dynamicWhereCondition(AssignmentSearchCondition condition, List<String> courseNames) {
		BooleanExpression baseCondition = course.courseName.in(courseNames);

		if (condition.getCourseName() != null) {
			baseCondition = baseCondition.and(courseNameEq(condition.getCourseName()));
		}
		if (condition.getWeek() != null) {
			baseCondition = baseCondition.and(weekEq(Week.valueOf(condition.getWeek())));
		}

		return baseCondition;
	}

	private BooleanExpression courseNameEq(String courseName) {
		return courseName == null ? null : course.courseName.eq(courseName);
	}

	private BooleanExpression weekEq(Week week) {
		return week == null ? null : nWeek.week.eq(week);
	}
}

package com.example.campushub.exam.repository;


import com.example.campushub.exam.dto.ExamFindAllResponse;
import com.example.campushub.exam.dto.ExamScoreInputRequest;
import com.example.campushub.exam.dto.ExamSearchCondition;
import com.example.campushub.exam.dto.QExamFindAllResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.campushub.course.domain.QCourse.*;
import static com.example.campushub.dept.domain.QDept.dept;
import static com.example.campushub.exam.domain.QExam.exam;
import static com.example.campushub.user.domain.QUser.user;
import static com.example.campushub.usercourse.domain.QUserCourse.userCourse;

@Repository
@RequiredArgsConstructor
public class ExamRepositoryCustomImpl implements ExamRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ExamFindAllResponse> findExamScoresByUserNums(List<String> userNums, ExamSearchCondition cond) {

        return queryFactory.select(new QExamFindAllResponse(
                        user.userName,
                        user.userNum,
                        dept.deptName,
                        userCourse.id,
                        exam.midScore,
                        exam.finalScore,
                        exam.totalScore
                )).from(userCourse)
                .leftJoin(exam).on(exam.userCourse.eq(userCourse)) // exam
                .join(user).on(userCourse.user.eq(user))
                .join(dept).on(user.dept.eq(dept)) // user dept
                .join(course).on(userCourse.course.eq(course))
                .where(user.userNum.in(userNums).and(course.courseName.eq(cond.getCourseName())))
                .fetch();
    }
    @Override
    @Transactional
    public void updateExamScore(ExamScoreInputRequest request) {
        queryFactory.update(exam)
                .set(exam.midScore, request.getMidScore())
                .set(exam.finalScore, request.getFinalScore())
                .set(exam.totalScore, request.getMidScore() + request.getFinalScore())
                .where(exam.id.eq(request.getExamId()))
                .execute();

    }
    private BooleanExpression eqUserCourseId(Long userCourseId) {
        return userCourseId != null ? exam.userCourse.id.eq(userCourseId) : null;
    }
}
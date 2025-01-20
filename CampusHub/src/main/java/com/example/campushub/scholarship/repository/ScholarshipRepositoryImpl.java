package com.example.campushub.scholarship.repository;


import com.example.campushub.scholarship.dto.QScholarshipResponseDto;
import com.example.campushub.scholarship.dto.ScholarshipResponseDto;
import com.example.campushub.scholarship.dto.ScholarshipSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.campushub.dept.domain.QDept.dept;
import static com.example.campushub.scholarship.domain.QScholarship.scholarship;
import static com.example.campushub.schoolyear.domain.QSchoolYear.schoolYear;
import static com.example.campushub.user.domain.QUser.user;
import static com.example.campushub.userscholarship.domain.QUserScholarship.userScholarship;

@Repository
@RequiredArgsConstructor
public class ScholarshipRepositoryImpl implements ScholarshipRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<ScholarshipResponseDto> findAllByCondition(ScholarshipSearchCondition cond) {
        return queryFactory.select(new QScholarshipResponseDto(
                        user.userName,
                        user.userNum,
                        dept.deptName,
                        schoolYear.year,
                        schoolYear.semester,
                        scholarship.scholarshipName,
                        scholarship.type,
                        scholarship.amount,
                        userScholarship.confDate
                )).from(scholarship)
                .join(userScholarship).on(userScholarship.scholarship.eq(scholarship))
                .join(userScholarship.user, user)
                .join(userScholarship.schoolYear, schoolYear)
                .join(user.dept, dept)
                .where(eqUserNum(cond.getUserNum()), eqDeptName(cond.getDeptName()))
                .fetch();
    }

    private BooleanExpression eqDeptName(String deptName) {
        return deptName == null ? null : dept.deptName.eq(deptName);
    }

    private BooleanExpression eqUserNum(String userNum) {
        return userNum == null ? null : user.userNum.eq(userNum);
    }

}

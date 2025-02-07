package com.example.campushub.tuition.repository;


import com.example.campushub.tuition.dto.*;
import com.example.campushub.usertuition.domain.PaymentStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.campushub.dept.domain.QDept.dept;
import static com.example.campushub.scholarship.domain.QScholarship.scholarship;
import static com.example.campushub.schoolyear.domain.QSchoolYear.schoolYear;
import static com.example.campushub.tuition.domain.QTuition.tuition;
import static com.example.campushub.user.domain.QUser.user;
import static com.example.campushub.userscholarship.domain.QUserScholarship.userScholarship;
import static com.example.campushub.usertuition.domain.QUserTuition.userTuition;

@RequiredArgsConstructor
@Repository
public class TuitionRepositoryCustomImpl implements TuitionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    //관리자 등록금 조회
    @Override
    public List<TuitionFindAllResponse> findAllByCondition(TuitionSearchCondition cond) {
        return queryFactory.select(new QTuitionFindAllResponse(user.userName,
                        user.userNum,
                        dept.deptName,
                        user.type,
                        userTuition.paymentStatus,
                        userTuition.paymentDate
                )).from(tuition)
                .join(userTuition).on(userTuition.tuition.eq(tuition))
                .join(user).on(userTuition.user.eq(user))
                .join(dept).on(user.dept.eq(dept))
                .where(eqDeptName(cond.getDeptName()),
                        eqUserNum(cond.getUserNum()),
                        eqPaymentStatus(PaymentStatus.of(cond.getPaymentStatus())))
                .fetch();
    }

    //학생 등록금 조회
    @Override
    @Transactional
    public Optional<TuitionStudentResponse> findStudentTuitionDetail(String userNum) {
        System.out.println("Executing UserNum: " + userNum);
        TuitionStudentResponse fetchOne = queryFactory.select(new QTuitionStudentResponse(
                        user.userNum,
                        user.userName,
                        dept.deptName,
                        schoolYear.year,
                        schoolYear.semester, tuition.tuitionFee,
                        scholarship.amount, userTuition.paymentStatus.stringValue()
                )).from(tuition)
                .join(userTuition).on(userTuition.tuition.eq(tuition))
                .join(user).on(userTuition.user.eq(user))
                .join(dept).on(user.dept.eq(dept))
                .join(schoolYear).on(userTuition.schoolYear.eq(schoolYear))
                .join(userScholarship).on(userScholarship.user.eq(user))
                .join(scholarship).on(userScholarship.scholarship.eq(scholarship))
                .where(
                        schoolYear.is_current.isTrue(), user.userNum.eq(userNum)
                )
                .fetchOne();

        //null 체크 및 로그
        if (fetchOne == null) {
            System.out.println("No tuition information found for userNum: " + userNum);
            System.out.println("username " + user.userName);
            System.out.println("dept.deptName " + dept.deptName);
            System.out.println("schoolYear.year: " + schoolYear.year);
            System.out.println("tuition.tuitionFee: " + tuition.tuitionFee);
            System.out.println("scholarship.amount: " + scholarship.amount);
            System.out.println("userTuition.paymentStatus.stringValue(): " + userTuition.paymentStatus.stringValue());
        }
        return Optional.ofNullable(fetchOne);

    }

    //학생 등록금 납부
    @Override
    @Transactional
    public void updatePaymentStatusToWaiting(String userNum) {
        queryFactory
                .update(userTuition)
                .set(userTuition.paymentStatus, PaymentStatus.WAITING)
                .where(
                        userTuition.user.userNum.eq(userNum),
                        userTuition.schoolYear.is_current.isTrue(),
                        userTuition.paymentStatus.eq(PaymentStatus.NONE)
                ).execute();
    }




    private BooleanExpression eqDeptName(String deptName) {
        return deptName == null ? null : dept.deptName.eq(deptName);
    }

    private BooleanExpression eqUserNum(String userNum) {
        return userNum == null ? null : user.userNum.eq(userNum);
    }

    private BooleanExpression eqPaymentStatus(PaymentStatus paymentStatus) {
        return paymentStatus == null ? null : userTuition.paymentStatus.eq(paymentStatus);
    }

}

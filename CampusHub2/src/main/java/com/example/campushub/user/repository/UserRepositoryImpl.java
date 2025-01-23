package com.example.campushub.user.repository;

import static com.example.campushub.dept.domain.QDept.*;
import static com.example.campushub.user.domain.QUser.*;

import java.util.List;
import java.util.Optional;

import com.example.campushub.user.domain.QUser;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.dto.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.campushub.user.domain.Status;
import com.example.campushub.user.domain.Type;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRepositoryImpl implements UserRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	//학생 컨디션 조회 및 전체 조회
	@Override
	public List<UserFindAllDto> findAllStudentByCondition(UserSearchCondition condition) {
		return queryFactory.select(new QUserFindAllDto(
			user.id,
			user.userNum,
			user.userName,
			dept.deptName,
			user.type,
			user.status
		))
			.from(user)
			.join(dept).on(user.dept.eq(dept))
			.where(statusEq(condition.getStatus()),
				userNumEq(condition.getUserNum()),
				deptNameEq(condition.getDeptName()),
				user.type.eq(Type.STUDENT))
			.fetch();
	}
	//교수 컨디션 조회 및 전체 조회
	@Override
	public List<UserFindAllDto> findAllProfessorByCondition(UserSearchCondition condition) {
		return queryFactory.select(new QUserFindAllDto(
			user.id,
			user.userNum,
			user.userName,
			dept.deptName,
			user.type,
			user.status
		))
			.from(user)
			.join(dept).on(user.dept.eq(dept))
			.where(userNumEq(condition.getUserNum()),
				deptNameEq(condition.getDeptName()),
				user.type.eq(Type.PROFESSOR))
			.fetch();
	}
	//학생 단건조회
	@Override
	public Optional<UserFindOneDto> getStudentByUserNum(String userNum) {
		UserFindOneDto fetchOne = queryFactory.select(new QUserFindOneDto(
			user.userNum,
			user.userName,
			user.birthday,
			dept.deptName,
			user.grade,
			user.email,
			user.phone,
			user.address
		))
			.from(user)
			.join(dept).on(dept.id.eq(user.dept.id))
			.where(user.userNum.eq(userNum),
				user.type.eq(Type.STUDENT))
			.fetchOne();
		return Optional.ofNullable(fetchOne);
	}

	//교수 단건 조회
	@Override
	public Optional<UserFindOneDto> getProfessorByUserNum(String userNum) {
		UserFindOneDto fetchOne = queryFactory.select(new QUserFindOneDto(
			user.userNum,
			user.userName,
			user.birthday,
			dept.deptName,
			user.grade,
			user.email,
			user.phone,
			user.address
		))
			.from(user)
			.join(dept).on(dept.id.eq(user.dept.id))
			.where(user.userNum.eq(userNum),
				user.type.eq(Type.PROFESSOR))
			.fetchOne();
		return Optional.ofNullable(fetchOne);
	}

	private BooleanExpression statusEq(Status status) {
		return status == null ? null : user.status.eq(status);
	}
	private BooleanExpression userNumEq(String userNum) {
		return userNum == null ? null : user.userNum.eq(userNum);
	}
	private BooleanExpression deptNameEq(String deptName) {
		return deptName == null ? null : dept.deptName.eq(deptName);
	}

}

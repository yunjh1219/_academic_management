package com.example.campushub.user.repository;

import java.util.List;
import java.util.Optional;

import com.example.campushub.user.domain.Type;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.dto.UserFindAllDto;
import com.example.campushub.user.dto.UserFindOneDto;
import com.example.campushub.user.dto.UserSearchCondition;
import org.hibernate.usertype.UserType;

public interface UserRepositoryCustom {
	//학생 컨디션, 전체 조회
	List<UserFindAllDto> findAllStudentByCondition(UserSearchCondition searchCondition);
	//교수 컨디션, 전체 조회
	List<UserFindAllDto> findAllProfessorByCondition(UserSearchCondition searchCondition);
	//학생단건조회
	Optional<UserFindOneDto> getStudentByUserNum(String userNum);
	//교수단건조회
	Optional<UserFindOneDto> getProfessorByUserNum(String userNum);

}
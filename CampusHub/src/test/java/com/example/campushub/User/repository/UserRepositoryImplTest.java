package com.example.campushub.User.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.campushub.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.example.campushub.dept.domain.Dept;
import com.example.campushub.dept.repository.DeptRepository;
import com.example.campushub.global.config.QueryDslConfig;
import com.example.campushub.user.domain.Grade;
import com.example.campushub.user.domain.Role;
import com.example.campushub.user.domain.Status;
import com.example.campushub.user.domain.Type;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.dto.UserFindOneDto;

@DataJpaTest
@Import(QueryDslConfig.class)
public class UserRepositoryImplTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	DeptRepository deptRepository;

	@AfterEach
	public void tearDown() {
		userRepository.deleteAll();
		deptRepository.deleteAll();
	}

	@Test
	@DisplayName("학생 단건 조회")
	public void findOneStudent() {
		//given

		Dept dept = deptRepository.save(Dept.builder()
			.deptName("컴퓨터 소프트웨어 학과")
			.build());

		User user = userRepository.save(createUser("2400123", dept));

		//when
		Optional<UserFindOneDto> get = userRepository.getStudentByUserNum(user.getUserNum());

		//then
		UserFindOneDto result = get.get();
		assertThat(result.getUserName()).isEqualTo("전영욱");
		assertThat(result.getDeptName()).isEqualTo("컴퓨터 소프트웨어 학과");
	}

	private User createUser(String userNum, Dept dept){
		return User.builder()
			.userNum(userNum)
			.password("123456")
			.userName("전영욱")
			.birthday(LocalDateTime.now())
			.dept(dept)
			.email("test@gmail.com")
			.phone("010-1234-5678")
			.address("경기도 성남시 분당구 정자로134")
			.role(Role.USER)
			.type(Type.STUDENT)
			.grade(Grade.FIRST_GRADE)
			.status(Status.ENROLLED)
			.build();
	}
}

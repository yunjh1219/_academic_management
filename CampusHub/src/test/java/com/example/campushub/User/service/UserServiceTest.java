package com.example.campushub.User.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.example.campushub.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
// import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.campushub.dept.domain.Dept;
import com.example.campushub.dept.repository.DeptRepository;
import com.example.campushub.user.domain.Grade;
import com.example.campushub.user.domain.Role;
import com.example.campushub.user.domain.Status;
import com.example.campushub.user.domain.Type;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.dto.UserFindAllDto;
import com.example.campushub.user.dto.UserFindOneDto;
import com.example.campushub.user.dto.UserSearchCondition;
import com.example.campushub.user.repository.UserRepository;


@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DeptRepository deptRepository;

	@AfterEach
	public void tearDown(){
		userRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("학생 단건조회")
	public void getStudentByUserNum() {
		//given
		Dept dept = deptRepository.save(createDept("건축과"));
		User student= userRepository.save(createStudent("김길동", "12343", dept, Status.ENROLLED));

		User admin = userRepository.save((createAdmin("0001")));


		LoginUser loginUser = createLoginUser(admin);

		//then
		UserFindOneDto result = userService.getStudentByUserNum(loginUser, "12343");

		//when

		assertThat(result).isNotNull();
		assertThat(result.getUserNum()).isEqualTo(student.getUserNum());
		assertThat(result.getUserName()).isEqualTo(student.getUserName());
		assertThat(result.getDeptName()).isEqualTo(dept.getDeptName());
	}
	@Test
	@DisplayName("학생 전체 + 컨디션 조회")
	public void getStudentByCondition() {
		//given
		Dept dept1 = deptRepository.save(createDept("컴소과"));
		Dept dept2 = deptRepository.save(createDept("건축과"));
		Dept dept3 = deptRepository.save(createDept("시디과"));

		User student1 = userRepository.save(createStudent("김길동", "240001", dept1, Status.BREAK_PENDING));
		User student2 = userRepository.save(createStudent("홍길동", "240002", dept2, Status.BREAK_PENDING));
		User student3 = userRepository.save(createStudent("박길동", "240003", dept3, Status.ENROLLED));

		User Admin = userRepository.save(createAdmin("0001"));
		LoginUser loginUser = createLoginUser(Admin);

		UserSearchCondition cond1 = createCondition("240001",null, null);
		UserSearchCondition cond2 = createCondition("240002",null, null);
		UserSearchCondition cond3 = createCondition(null,null, null);
		UserSearchCondition cond4 = createCondition(null,"컴소과", null);
		UserSearchCondition cond5 = createCondition(null,null, Status.ENROLLED);

		//when
		List<UserFindAllDto> result1 = userService.getStudentByCondition(loginUser, cond1);
		List<UserFindAllDto> result2 = userService.getStudentByCondition(loginUser, cond2);
		List<UserFindAllDto> result3 = userService.getStudentByCondition(loginUser, cond3);
		List<UserFindAllDto> result4 = userService.getStudentByCondition(loginUser, cond4);
		List<UserFindAllDto> result5 = userService.getStudentByCondition(loginUser, cond5);


		//then
		assertThat(result1.size()).isEqualTo(1);
		assertThat(result1.get(0).getUserNum()).isEqualTo(student1.getUserNum());
		assertThat(result2.size()).isEqualTo(1);
		assertThat(result2.get(0).getUserNum()).isEqualTo(student2.getUserNum());
		assertThat(result3.size()).isEqualTo(3);
		assertThat(result3.get(0).getUserNum()).isEqualTo(student1.getUserNum());
		assertThat(result3.get(1).getUserNum()).isEqualTo(student2.getUserNum());
		assertThat(result3.get(2).getUsername()).isEqualTo(student3.getUserName());
		assertThat(result4.size()).isEqualTo(1);
		assertThat(result4.get(0).getUserNum()).isEqualTo(student1.getUserNum());
		assertThat(result5.size()).isEqualTo(1);
		assertThat(result5.get(0).getUserNum()).isEqualTo(student3.getUserNum());

	}

	@Test
	@DisplayName("교수 단건조회")
	public void getProfessorByUserNum() {
		//given
		Dept dept = deptRepository.save(createDept("컴소과"));
		User professor = userRepository.save(createProfessor("김교수", "240101", dept));

		User admin = userRepository.save(createAdmin("0001"));
		LoginUser loginUser = createLoginUser(admin);

		//when
		UserFindOneDto result = userService.getProfessorByUserNum(loginUser, "240101");

		//then
		assertThat(result).isNotNull();
		assertThat(result.getUserNum()).isEqualTo(professor.getUserNum());
		assertThat(result.getUserName()).isEqualTo(professor.getUserName());
		assertThat(result.getDeptName()).isEqualTo(professor.getDept().getDeptName());
	}

	@Test
	@DisplayName("교수 전체 + 컨디션 조회")
	public void getProfessorByCondition() {
		//given
		Dept dept1 = deptRepository.save(createDept("컴소과"));
		Dept dept2 = deptRepository.save(createDept("건축과"));
		Dept dept3 = deptRepository.save(createDept("컴소과"));

		User prof1 = userRepository.save(createProfessor("김교수", "240101", dept1));
		User prof2 = userRepository.save(createProfessor("박교수", "240102", dept2));
		User prof3 = userRepository.save(createProfessor("최교수", "240103", dept3));

		User admin = userRepository.save(createAdmin("0001"));
		LoginUser loginUser = createLoginUser(admin);

		UserSearchCondition cond1 = createCondition("240101",null, null);
		UserSearchCondition cond2 = createCondition("240102", null, null);
		UserSearchCondition cond3 = createCondition(null, "컴소과", null);
		UserSearchCondition cond4 = createCondition(null, "건축과", null);

		//when
		List<UserFindAllDto> result1 = userService.getProfessorByCondition(loginUser, cond1);
		List<UserFindAllDto> result2 = userService.getProfessorByCondition(loginUser, cond2);
		List<UserFindAllDto> result3 = userService.getProfessorByCondition(loginUser, cond3);
		List<UserFindAllDto> result4 = userService.getProfessorByCondition(loginUser, cond4);

		//then
		assertThat(result1.size()).isEqualTo(1);
		assertThat(result1.get(0).getUserNum()).isEqualTo(prof1.getUserNum());
		assertThat(result2.size()).isEqualTo(1);
		assertThat(result2.get(0).getUserNum()).isEqualTo(prof2.getUserNum());
		assertThat(result3.size()).isEqualTo(2);
		assertThat(result3.get(0).getUserNum()).isEqualTo(prof1.getUserNum());
		assertThat(result3.get(1).getUserNum()).isEqualTo(prof3.getUserNum());
		assertThat(result4.size()).isEqualTo(1);
		assertThat(result4.get(0).getUserNum()).isEqualTo(prof2.getUserNum());
	}

	@Test
	@DisplayName("학적 상태 변경(관리자)")
	@Transactional
	public void updateUserStatus() {
		//given
		Dept dept = deptRepository.save(createDept("컴소과"));
		User student1 = userRepository.save(createStudent("김길동", "1000", dept,Status.BREAK_PENDING));
		User student2 = userRepository.save(createStudent("박개똥", "1001", dept, Status.BREAK_PENDING));
		User student3 = userRepository.save(createStudent("최동구", "1002", dept, Status.BREAK_PENDING));

		List<String> userNums = Arrays.asList(student1.getUserNum(), student2.getUserNum(), student3.getUserNum());

		User admin = userRepository.save(createAdmin("0001"));
		LoginUser loginUser = createLoginUser(admin);

		//when
		userService.updateUserStatus(loginUser, userNums);
		//then
		assertThat(student1.getStatus()).isEqualTo(Status.BREAK);

	}
	private UserSearchCondition createCondition(String userNum, String deptName, Status status) {
		return UserSearchCondition.builder()
			.userNum(userNum)
			.deptName(deptName)
			.status(status)
			.build();
	}

	private LoginUser createLoginUser(User user) {
		return LoginUser.builder()
			.userNum(user.getUserNum())
			.role(user.getRole())
			.type(user.getType())
			.build();
	}

	private Dept createDept(String deptName) {
		return Dept.builder()
			.deptName(deptName)
			.build();
	}

	private User createProfessor(String userName, String userNum, Dept dept){
		return User.builder()
			.userName(userName)
			.userNum(userNum)
			.password("1234")
			.birthday(LocalDateTime.now())
			.email("aaa@gmail.com")
			.dept(dept)
			.phone("010-1234-5678")
			.address("경기도 성남시 분당구")
			.role(Role.USER)
			.type(Type.PROFESSOR)
			.status(Status.EMPLOYED)
			.build();
	}

	private User createStudent(String userName, String userNum, Dept dept, Status status) {
		return User.builder()
			.userName(userName)
			.userNum(userNum)
			.password("1234")
			.birthday(LocalDateTime.now())
			.email("aaa@gmail.com")
			.dept(dept)
			.phone("010-1234-5678")
			.address("경기도 성남시 분당구")
			.role(Role.USER)
			.type(Type.STUDENT)
			.grade(Grade.FIRST_GRADE)
			.status(status)
			.build();
	}

	private User createAdmin(String userNum) {
		return User.builder()
			.userName("김관리")
			.userNum(userNum)
			.password("1234")
			.email("aaa@gmail.com")
			.phone("010-1234-5678")
			.role(Role.ADMIN)
			.type(Type.ADMIN)
			.build();
	}


}

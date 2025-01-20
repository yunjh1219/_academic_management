package com.example.campushub.scholarship.repository;

import com.example.campushub.dept.domain.Dept;
import com.example.campushub.dept.repository.DeptRepository;
import com.example.campushub.global.config.QueryDslConfig;
import com.example.campushub.scholarship.domain.PaymentType;
import com.example.campushub.scholarship.domain.Scholarship;
import com.example.campushub.scholarship.dto.ScholarshipResponseDto;
import com.example.campushub.scholarship.dto.ScholarshipSearchCondition;
import com.example.campushub.schoolyear.domain.SchoolYear;
import com.example.campushub.schoolyear.domain.Semester;
import com.example.campushub.schoolyear.repository.SchoolYearRepository;
import com.example.campushub.user.domain.Grade;
import com.example.campushub.user.domain.Role;
import com.example.campushub.user.domain.Status;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.repository.UserRepository;
import com.example.campushub.userscholarship.domain.UserScholarship;
import com.example.campushub.userscholarship.repository.UserScholarshipRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QueryDslConfig.class)
public class ScholarshipRepositoryImplTest {

    @Autowired
    ScholarshipRepository scholarshipRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserScholarshipRepository userScholarshipRepository;

    @Autowired
    SchoolYearRepository schoolYearRepository;

    @Autowired
    DeptRepository deptRepository;

    @AfterEach
    public void tearDown() {
        userScholarshipRepository.deleteAllInBatch();
        scholarshipRepository.deleteAllInBatch();
        schoolYearRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        deptRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("장학금 전체 조회")
    public void findAllByCond() {
        // given
        Dept dept = deptRepository.save(Dept.builder()
                .deptName("컴퓨터 소프트웨어 학과")
                .build());

        User userA = userRepository.save(createUser("200001", dept));
        User userB = userRepository.save(createUser("200002", dept));

        Scholarship scholarship = scholarshipRepository.save(createScholarship());

        SchoolYear schoolYear = schoolYearRepository.save(createSchoolYear());

        userScholarshipRepository.save(createUserScholarship(userA, scholarship, schoolYear));
        userScholarshipRepository.save(createUserScholarship(userB, scholarship, schoolYear));


        // when
        List<ScholarshipResponseDto> result = scholarshipRepository.findAllByCondition(ScholarshipSearchCondition.of(null, null));

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("장학금 전체 조회 - 학과 조건")
    public void findAllByDeptCond() {
        // given
        Dept softwareDept = deptRepository.save(createDept("컴퓨터 소프트웨어 학과"));
        Dept sportsDept = deptRepository.save(createDept("레저 스포츠 학과"));

        User userA = userRepository.save(createUser("200001", softwareDept));
        User userB = userRepository.save(createUser("200002", sportsDept));

        Scholarship scholarship = scholarshipRepository.save(createScholarship());

        SchoolYear schoolYear = schoolYearRepository.save(createSchoolYear());

        userScholarshipRepository.save(createUserScholarship(userA, scholarship, schoolYear));
        userScholarshipRepository.save(createUserScholarship(userB, scholarship, schoolYear));

        // when
        List<ScholarshipResponseDto> result = scholarshipRepository.findAllByCondition(ScholarshipSearchCondition.of(sportsDept.getDeptName(), null));

        // then
        ScholarshipResponseDto findScholarshipResult = result.get(0);
        assertThat(result.size()).isEqualTo(1);
        assertThat(findScholarshipResult.getDeptName()).isEqualTo("레저 스포츠 학과");
    }

    @Test
    @DisplayName("장학금 전체 조회 - 학번 조건")
    public void findAllByUserNumCond() {
        // given
        Dept softwareDept = deptRepository.save(createDept("컴퓨터 소프트웨어 학과"));
        Dept sportsDept = deptRepository.save(createDept("레저 스포츠 학과"));

        User userA = userRepository.save(createUser("200001", softwareDept));
        User userB = userRepository.save(createUser("200002", sportsDept));

        Scholarship scholarship = scholarshipRepository.save(createScholarship());

        SchoolYear schoolYear = schoolYearRepository.save(createSchoolYear());

        userScholarshipRepository.save(createUserScholarship(userA, scholarship, schoolYear));
        userScholarshipRepository.save(createUserScholarship(userB, scholarship, schoolYear));

        // when
        List<ScholarshipResponseDto> result = scholarshipRepository.findAllByCondition(ScholarshipSearchCondition.of(null, userA.getUserNum()));

        // then
        ScholarshipResponseDto findScholarshipResult = result.get(0);
        assertThat(result.size()).isEqualTo(1);
        assertThat(findScholarshipResult.getUserNum()).isEqualTo(userA.getUserNum());
    }


    private User createUser(String userNum, Dept dept) {
        return User.builder()
                .userName("박" + userNum)
                .userNum(userNum)
                .password("1234")
                .email(userNum + "@gmail.com")
                .dept(dept)
                .phone(userNum)
                .role(Role.USER)
                .grade(Grade.FIRST_GRADE)
                .status(Status.BREAK)
                .build();
    }

    private Scholarship createScholarship() {
        return Scholarship.builder()
                .scholarshipName("성적장학금")
                .type(PaymentType.PRE_PAYMENT)
                .amount(1000000)
                .build();
    }

    private SchoolYear createSchoolYear() {
        return SchoolYear.builder()
                .year(LocalDate.now())
                .semester(Semester.first_semester)
                .is_current(true)
                .build();
    }

    private UserScholarship createUserScholarship(User user, Scholarship scholarship, SchoolYear schoolYear) {
        return UserScholarship.builder()
                .user(user)
                .scholarship(scholarship)
                .schoolYear(schoolYear)
                .confDate(LocalDate.now())
                .build();
    }

    private Dept createDept(String deptName) {
        return Dept.builder()
                .deptName(deptName)
                .build();
    }

}

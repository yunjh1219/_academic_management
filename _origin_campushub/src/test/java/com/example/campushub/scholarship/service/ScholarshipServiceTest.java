//package com.example.campushub.scholarship.service;
//
//
//import com.example.campushub.dept.domain.Dept;
//import com.example.campushub.dept.repository.DeptRepository;
//import com.example.campushub.global.config.QueryDslConfig;
//import com.example.campushub.global.error.exception.UserNotFoundException;
//import com.example.campushub.scholarship.domain.PaymentType;
//import com.example.campushub.scholarship.domain.Scholarship;
//import com.example.campushub.scholarship.dto.GetMyScholarshipDto;
//import com.example.campushub.scholarship.dto.ScholarshipCreateDto;
//import com.example.campushub.scholarship.dto.ScholarshipResponseDto;
//import com.example.campushub.scholarship.dto.ScholarshipSearchCondition;
//import com.example.campushub.scholarship.repository.ScholarshipRepository;
//import com.example.campushub.schoolyear.domain.SchoolYear;
//import com.example.campushub.schoolyear.domain.Semester;
//import com.example.campushub.schoolyear.repository.SchoolYearRepository;
//import com.example.campushub.user.domain.*;
//import com.example.campushub.user.dto.LoginUser;
//import com.example.campushub.user.dto.UserFindOneSimpleDto;
//import com.example.campushub.user.repository.UserRepository;
//import com.example.campushub.userscholarship.domain.UserScholarship;
//import com.example.campushub.userscholarship.repository.UserScholarshipRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class ScholarshipServiceTest {
//
//    @Autowired
//    ScholarshipRepository scholarshipRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    UserScholarshipRepository userScholarshipRepository;
//
//    @Autowired
//    SchoolYearRepository schoolYearRepository;
//
//    @Autowired
//    DeptRepository deptRepository;
//
//    @Autowired
//    ScholarshipService scholarshipService;
//
//    @AfterEach
//    public void tearDown() {
//        userScholarshipRepository.deleteAllInBatch();
//        scholarshipRepository.deleteAllInBatch();
//        schoolYearRepository.deleteAllInBatch();
//        userRepository.deleteAllInBatch();
//        deptRepository.deleteAllInBatch();
//    }
//
//    @Test
//    @DisplayName("본인 장학금 조회")
//    public void findMyScholarship() {
//        //given
//        Dept dept1 = deptRepository.save(createDept("시디과"));
//        User student = userRepository.save(createStudent("1234", "전영욱", dept1));
//
//
//        LoginUser userA = createLoginUser(student);
//
//        Scholarship scholarshipA = scholarshipRepository.save(createCustomScholarship("성적장학금"));
//        Scholarship scholarshipB = scholarshipRepository.save(createCustomScholarship("국가장학금"));
//        Scholarship scholarshipC = scholarshipRepository.save(createCustomScholarship("교수추천장학금"));
//
//        SchoolYear schoolYear = schoolYearRepository.save(createSchoolYear());
//
//        userScholarshipRepository.save(createUserScholarship(student, scholarshipA,schoolYear));
//        userScholarshipRepository.save(createUserScholarship(student, scholarshipB,schoolYear));
//        userScholarshipRepository.save(createUserScholarship(student, scholarshipC,schoolYear));
//
//
//
//
//        //when
//        List<GetMyScholarshipDto> resultA = scholarshipService.findMyScholarships(userA);
//
//
//        //then
//        assertThat(resultA).hasSize(3);
//
//    }
//    @Test
//    @DisplayName("장학금 등록")
//    @Transactional
//    public void createScholarshipTest() {
//        // given
//        Dept dept2 = deptRepository.save(createDept("컴소과"));
//        User admin = userRepository.save(createAdmin("12345"));
//        User student = userRepository.save(createStudent("1906078", "전영욱", dept2));
//
//        // 이미 생성된 schoolYear를 그대로 사용
//        SchoolYear schoolYear = createSchoolYear();
//
//
//        LoginUser admin1 = createLoginUser(admin);
//
//        // createDto에서 schoolYear를 바로 사용
//        ScholarshipCreateDto createDto = ScholarshipCreateDto.builder()
//                .userNum(student.getUserNum())
//                .schoolYear(schoolYear)  // 이미 저장된 schoolYear 객체 사용
//                .scholarshipName("성적장학금")
//                .type(PaymentType.PRE_PAYMENT)
//                .amount(100000)
//                .build();
//
//        // when
//        scholarshipService.createScholarship(createDto, admin1);
//
//        // then
//        Scholarship result = scholarshipRepository.findScholarshipByScholarshipName(createDto.getScholarshipName());
//        UserScholarship result2 = userScholarshipRepository.findUserScholarshipByScholarship(result);
//
//        List<Scholarship> scholarships = scholarshipRepository.findAll();
//        List<UserScholarship> userScholarships = userScholarshipRepository.findAll();
//        List<SchoolYear> schoolYears = schoolYearRepository.findAll();
//
//        assertThat(scholarships).hasSize(1);
//        assertThat(userScholarships).hasSize(1);
//        assertThat(schoolYears).hasSize(1);
//
//        UserScholarship savedUserScholarship = userScholarships.get(0);
//        assertThat(savedUserScholarship.getUser()).isEqualTo(student);
//        assertThat(savedUserScholarship.getScholarship().getScholarshipName()).isEqualTo("성적장학금");
//    }
//
//    @Test
//    @DisplayName("이름 학과 자동 조회 서비스")
//    public void autoUserInfo(){
//
//        //given
//
//        Dept dept2 = deptRepository.save(createDept("컴소과"));
//
//        User student = userRepository.save(createStudent("1906078", "전영욱", dept2));
//
//        //when
//        UserFindOneSimpleDto result = scholarshipService.getUserSimpleInfo(student.getUserNum());
//
//
//        //then
//        assertThat(result).isNotNull();
//        assertThat(result.getUserName()).isEqualTo(student.getUserName());
//        assertThat(result.getDeptName()).isEqualTo(student.getDept().getDeptName());
//        assertThat(result.getUserNum()).isEqualTo(student.getUserNum());
//
//    }
//
//    private User createUserNum(String userNum) {
//        return User.builder()
//                .userNum(userNum)
//                .build();
//    }
//    private User createAdmin(String userNum) {
//        return User.builder()
//                .userName("김관리")
//                .userNum(userNum)
//                .password("1234")
//                .email("aaa@gmail.com")
//                .phone("010-234-5235")
//                .role(Role.ADMIN)
//                .type(Type.ADMIN)
//                .build();
//    }
//    private User createStudent(String userNum, String userName, Dept dept) {
//        return User.builder()
//                .userName(userName)
//                .userNum(userNum)
//                .password("1234")
//                .email("aaa@gmail.com")
//                .dept(dept)
//                .phone("010-234-5235")
//                .role(Role.USER)
//                .grade(Grade.FIRST_GRADE)
//                .type(Type.STUDENT)
//                .status(Status.BREAK)
//                .build();
//    }
//
//    private Scholarship createScholarship(String scholarshipName) {
//        return Scholarship.builder()
//                .scholarshipName(scholarshipName)
//                .type(PaymentType.PRE_PAYMENT)
//                .amount(1000000)
//                .build();
//    }
//
//    private SchoolYear createSchoolYear() {
//        return SchoolYear.builder()
//                .year(LocalDate.now())
//                .semester(Semester.first_semester)
//                .is_current(true)
//                .build();
//    }
//
//    private UserScholarship createUserScholarship(User user, Scholarship scholarship, SchoolYear schoolYear) {
//        return UserScholarship.builder()
//                .user(user)
//                .scholarship(scholarship)
//                .schoolYear(schoolYear)
//                .confDate(LocalDate.now())
//                .build();
//    }
//
//    private Dept createDept(String deptName) {
//        return Dept.builder()
//                .deptName(deptName)
//                .build();
//    }
//
//    private Scholarship createCustomScholarship(String scholarshipName){
//        return Scholarship.builder()
//                .scholarshipName(scholarshipName)
//                .type(PaymentType.PRE_PAYMENT)
//                .amount(1000000)
//                .build();
//    }
//
//    private LoginUser createLoginUser(User user) {
//        return LoginUser.builder()
//                .userNum(user.getUserNum())
//                .role(Role.ADMIN)
//                .type(Type.ADMIN)
//                .build();
//    }
//
//    private ScholarshipCreateDto createScholarships(String scholarshipName) {
//        return ScholarshipCreateDto.builder()
//                .scholarshipName(scholarshipName)
//                .schoolYear(createSchoolYear())
//                .type(PaymentType.PRE_PAYMENT)
//                .amount(1000000)
//                .build();
//    }
//
//    private UserFindOneSimpleDto userFindDto(String userNum,String userName,String deptName) {
//        return UserFindOneSimpleDto.builder()
//                .userNum(userNum)
//                .userName(userName)
//                .deptName(deptName)
//                .build();
//    }
//
//}

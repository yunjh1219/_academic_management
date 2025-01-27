//package com.example.campushub.attendance.repository;
//
//
//import com.example.campushub.attendance.service.AttendanceService;
//import com.example.campushub.course.domain.Course;
//import com.example.campushub.course.repository.CourseRepository;
//import com.example.campushub.global.config.QueryDslConfig;
//import com.example.campushub.nweek.domain.NWeek;
//import com.example.campushub.nweek.domain.Week;
//import com.example.campushub.nweek.repository.NweekRepository;
//import com.example.campushub.user.domain.*;
//import com.example.campushub.user.dto.LoginUser;
//import com.example.campushub.user.repository.UserRepository;
//import com.example.campushub.usercourse.domain.UserCourse;
//import com.example.campushub.usercourse.repository.UserCourseRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class AttendanceRepositoryTest {
//    @Autowired
//    private AttendanceRepository attendanceRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserCourseRepository userCourseRepository;
//    @Autowired
//    private CourseRepository courseRepository;
//    @Autowired
//    private AttendanceService attendanceService;
//    @Autowired
//    private NweekRepository nweekRepository;
//
//
//
//    @AfterEach
//    public void tearDown(){
//        attendanceRepository.deleteAllInBatch();
//        userRepository.deleteAllInBatch();
//        courseRepository.deleteAllInBatch();
//        userCourseRepository.deleteAllInBatch();
//        nweekRepository.deleteAllInBatch();
//    }
//    @Test
//    @DisplayName("장학금 조회 - 강의")
//    public void findStudentsByCourseName() {
//
//        // Given: 데이터 준비
//        User professor = userRepository.save(createProfessor("12412"));
//        LoginUser loginUser = createLoginUser(professor);
//
//        User userA = userRepository.save(createUser("학생1", "1906078"));
//        User userB = userRepository.save(createUser("학생2", "1906088"));
//        User userC = userRepository.save(createUser("학생3", "1906098"));
//
//        Course courseA = courseRepository.save(createCourse("시디과"));
//        Course courseB = courseRepository.save(createCourse("컴소과"));
//
//        userCourseRepository.save(createUserCourse(userA, courseA));
//        userCourseRepository.save(createUserCourse(userB, courseA));
//        userCourseRepository.save(createUserCourse(userC, courseA));  // "시디과"에 3명의 학생을 등록
//
//        // When: "시디과" 강의에 대한 UserCourse 조회
//        List<UserCourse> result = userCourseRepository.findAllByCourse(courseA);  // findAllByCourse(courseA) 메서드는 필요하다면 추가해야 함
//
//        // Then: "시디과" 강의에 등록된 학생이 2명이 아니고 3명인지 확인
//        assertThat(result.size()).isEqualTo(3);
//    }
//
//
//
//    private LoginUser createLoginUser(User user) {
//        return LoginUser.builder()
//                .userNum(user.getUserNum())
//                .role(user.getRole())
//                .type(user.getType())
//                .build();
//    }
//
//    private User createProfessor(String userNum) {
//        return User.builder()
//                .userName("전영욱")
//                .userNum(userNum)
//                .password("1234")
//                .email("test@aaa.com")
//                .phone("1029301923801")
//                .role(Role.USER)
//                .type(Type.PROFESSOR)
//                .grade(Grade.FIRST_GRADE)
//                .status(Status.BREAK)
//                .build();
//    }
//
//    private User createUser(String userName,String userNum) {
//        return User.builder()
//                .userName(userName)
//                .userNum(userNum)
//                .password("1234")
//                .email("test@aaa.com")
//                .phone("1029301923801")
//                .role(Role.USER)
//                .type(Type.PROFESSOR)
//                .grade(Grade.FIRST_GRADE)
//                .status(Status.BREAK)
//                .build();
//    }
//
//    private Course createCourse(String courseName){
//        return Course.builder()
//                .courseName(courseName)
//                .build();
//
//    }
//    private UserCourse createUserCourse(User user, Course course){
//        return UserCourse.builder()
//                .user(user)
//                .course(course)
//                .build();
//
//    }
//
//
//
//
//    private NWeek createNWeek(){
//        return NWeek.builder()
//                .week(Week.FIRST)
//                .build();
//    }
//
//
//}

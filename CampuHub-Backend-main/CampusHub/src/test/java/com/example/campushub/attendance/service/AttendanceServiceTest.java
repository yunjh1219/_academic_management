// package com.example.campushub.attendance.service;
//
//
// import com.example.campushub.attendance.domain.Attendance;
// import com.example.campushub.attendance.domain.AttendanceStatus;
// import com.example.campushub.attendance.dto.AllAttendanceResponseDto;
// import com.example.campushub.attendance.dto.AttendanceResponseDto;
// import com.example.campushub.attendance.dto.AttendanceSearchCondition;
// import com.example.campushub.attendance.dto.AttendanceSearchCourseCondition;
// import com.example.campushub.attendance.repository.AttendanceRepository;
// import com.example.campushub.course.domain.Course;
// import com.example.campushub.course.repository.CourseRepository;
// import com.example.campushub.dept.domain.Dept;
// import com.example.campushub.global.config.QueryDslConfig;
// import com.example.campushub.nweek.domain.NWeek;
// import com.example.campushub.nweek.domain.Week;
// import com.example.campushub.nweek.repository.NweekRepository;
// import com.example.campushub.user.domain.*;
// import com.example.campushub.user.dto.LoginUser;
// import com.example.campushub.user.repository.UserRepository;
// import com.example.campushub.usercourse.domain.UserCourse;
// import com.example.campushub.usercourse.repository.UserCourseRepository;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.context.annotation.Import;
//
// import java.util.List;
//
// import static org.assertj.core.api.Assertions.assertThat;
//
// @SpringBootTest
// public class AttendanceServiceTest {
//     @Autowired
//     private AttendanceRepository attendanceRepository;
//     @Autowired
//     private UserRepository userRepository;
//     @Autowired
//     private UserCourseRepository userCourseRepository;
//     @Autowired
//     private CourseRepository courseRepository;
//     @Autowired
//     private AttendanceService attendanceService;
//     @Autowired
//     private NweekRepository nweekRepository;
//
//
//
//     @AfterEach
//     public void tearDown() {
//         attendanceRepository.deleteAllInBatch(); // 참조 테이블부터 삭제
//         nweekRepository.deleteAllInBatch();      // 참조 테이블부터 삭제
//         userCourseRepository.deleteAllInBatch(); // 참조하는 테이블 삭제
//         courseRepository.deleteAllInBatch();
//         userRepository.deleteAllInBatch();       // 마지막으로 최상위 테이블 삭제
//     }
//     @Test
//     @DisplayName("출석 조회")
//     public void findStudentsByCourseName() {
//
//         // Given: 데이터 준비
//         User professor = userRepository.save(createProfessor("12412"));
//         LoginUser loginUser = createLoginUser(professor);
//
//         User userA = userRepository.save(createUser("학생1", "1906078"));
//         User userB = userRepository.save(createUser("학생2", "1906088"));
//         User userC = userRepository.save(createUser("학생3", "1906098"));
//
//         Course courseA = courseRepository.save(createCourse("시디과"));
//         Course courseB = courseRepository.save(createCourse("컴소과"));
//
//         NWeek week1 = nweekRepository.save(createNWeek());
//
//         UserCourse userCourse1= userCourseRepository.save(createUserCourse(userA, courseA));
//         UserCourse userCourse2= userCourseRepository.save(createUserCourse(userB, courseA));
//         UserCourse userCourse3= userCourseRepository.save(createUserCourse(userC, courseA));// "시디과"에 3명의 학생을 등록
//
//         attendanceRepository.save(createAttendance(week1,userCourse1));
//         attendanceRepository.save(createAttendance(week1,userCourse2));
//         attendanceRepository.save(createAttendance(week1,userCourse3));
//
//         AttendanceSearchCondition cond = AttendanceSearchCondition.builder()
//                 .week(week1.getWeek())
//                 .courseName(courseA.getCourseName())
//                 .build();
//
//         // When: "시디과" 강의에 대한 UserCourse 조회
// //        List<UserCourse> result = userCourseRepository.findAllByCourse(courseA);  // findAllByCourse(courseA) 메서드는 필요하다면 추가해야 함
//         List<AttendanceResponseDto> result2 = attendanceService.findAttendance(cond,loginUser);
//
//
//         // Then: "시디과" 강의에 등록된 학생이 2명이 아니고 3명인지 확인
//
//         assertThat(result2.size()).isEqualTo(3);
//
//
//
//
//     }
//
//
//     @Test
//     @DisplayName("출석 등록")
//     public void setupUserAttendance() {
//
//         // Given: 데이터 준비
//         User professor = userRepository.save(createProfessor("12412"));
//         LoginUser loginUser = createLoginUser(professor);
//
//
//         User studentA = userRepository.save(createUser("학생A", "1901001"));
//         User studentB = userRepository.save(createUser("학생B", "1901002"));
//
//         Course course = courseRepository.save(createCourse("컴퓨터 공학"));
//
//         NWeek week = nweekRepository.save(createNWeek());
//
//         UserCourse userCourseA = userCourseRepository.save(createUserCourse(studentA, course));
//         UserCourse userCourseB = userCourseRepository.save(createUserCourse(studentB, course));
//
//         // AttendanceResponseDto 객체 생성
//         AttendanceResponseDto attendanceA = AttendanceResponseDto.builder()
//                 .userNum("1901001")
//                 .status(AttendanceStatus.ATTENDANCE)
//                 .build();
//
//         AttendanceResponseDto attendanceB = AttendanceResponseDto.builder()
//                 .userNum("1901002")
//                 .status(AttendanceStatus.ABSENCE)
//                 .build();
//
//
//
//         AttendanceSearchCondition cond = AttendanceSearchCondition.builder()
//                 .week(week.getWeek())
//                 .courseName(course.getCourseName())
//                 .build();
//
//         List<AttendanceResponseDto> attendances = List.of(attendanceA, attendanceB);
//
//         //when
//         attendanceService.createAttendance(loginUser,attendances,cond);
//
//
//
//         //then
//         List<Attendance> savedAttendances = attendanceRepository.findAll();
//
//         assertThat(savedAttendances).hasSize(2);
//
//
//
//     }
//
//     @Test
//     @DisplayName("출석통계 조회")
//     public void findAllWeekAttendance() {
//
//         //given
//         User professor = userRepository.save(createProfessor("12412"));
//         LoginUser loginUser = createLoginUser(professor);
//
//
//         User studentA = userRepository.save(createUser("학생A", "1901001"));
//         User studentB = userRepository.save(createUser("학생B", "1901002"));
//
//         Course course = courseRepository.save(createCourse("컴소과"));
//
//         NWeek week1 = nweekRepository.save(createNWeek());
//
//
//         UserCourse userCourseA = userCourseRepository.save(createUserCourse(studentA, course));
//         UserCourse userCourseB = userCourseRepository.save(createUserCourse(studentB, course));
//
//         attendanceRepository.save(createAttendance(week1,userCourseA));
//         attendanceRepository.save(createAttendance(week1,userCourseB));
//
//         AttendanceSearchCourseCondition cond = AttendanceSearchCourseCondition.builder()
//                 .courseName(course.getCourseName())
//                 .build();
//
//
//         //when
//         List<AllAttendanceResponseDto> result = attendanceService.findAllAttendance(loginUser,cond);
//
//         //then
//
//         assertThat(result.size()).isEqualTo(2);
//
//     }
//
//
//
//
//     private LoginUser createLoginUser(User user) {
//         return LoginUser.builder()
//                 .userNum(user.getUserNum())
//                 .role(user.getRole())
//                 .type(user.getType())
//                 .build();
//     }
//
//     private User createProfessor(String userNum) {
//         return User.builder()
//                 .userName("전영욱")
//                 .userNum(userNum)
//                 .password("1234")
//                 .email("test@aaa.com")
//                 .phone("1029301923801")
//                 .role(Role.USER)
//                 .type(Type.PROFESSOR)
//                 .grade(Grade.FIRST_GRADE)
//                 .status(Status.BREAK)
//                 .build();
//     }
//
//     private User createUser(String userName,String userNum) {
//         return User.builder()
//                 .userName(userName)
//                 .userNum(userNum)
//                 .password("1234")
//                 .email("test@aaa.com")
//                 .phone("1029301923801")
//                 .role(Role.USER)
//                 .type(Type.PROFESSOR)
//                 .grade(Grade.FIRST_GRADE)
//                 .status(Status.BREAK)
//                 .build();
//     }
//
//     private Dept createDept(String deptName) {
//         return Dept.builder()
//                 .deptName(deptName)
//                 .build();
//     }
//
//     private Course createCourse(String courseName){
//         return Course.builder()
//                 .courseName(courseName)
//                 .build();
//
//     }
//     private UserCourse createUserCourse(User user, Course course){
//         return UserCourse.builder()
//                 .user(user)
//                 .course(course)
//                 .build();
//
//     }
//
//
//
//
//     private NWeek createNWeek(){
//         return NWeek.builder()
//                 .week(Week.FIRST)
//                 .build();
//     }
//
//     private Attendance createAttendance(NWeek week,UserCourse userCourse){
//         return Attendance.builder()
//                 .nWeek(week)
//                 .userCourse(userCourse)
//                 .status(AttendanceStatus.ATTENDANCE)
//                 .build();
//     }
//
//     private NWeek createAllWeek(){
//         return NWeek.builder()
//                 .week(Week.FIRST)
//                 .week(Week.SECOND)
//                 .week(Week.THIRD)
//                 .week(Week.FOURTH)
//                 .week(Week.FIFTH)
//                 .week(Week.SIXTH)
//                 .week(Week.SEVENTH)
//                 .week(Week.EIGHTH)
//                 .week(Week.NINTH)
//                 .week(Week.TENTH)
//                 .week(Week.ELEVENTH)
//                 .week(Week.TWELFTH)
//                 .week(Week.THIRTEENTH)
//                 .week(Week.FOURTEENTH)
//                 .week(Week.FIFTEENTH)
//                 .week(Week.SIXTEENTH)
//                 .build();
//     }
//
//
// }

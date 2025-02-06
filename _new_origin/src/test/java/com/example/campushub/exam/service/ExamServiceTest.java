// package com.example.campushub.exam.service;
//
// import com.example.campushub.dept.domain.Dept;
// import com.example.campushub.dept.repository.DeptRepository;
// import com.example.campushub.exam.domain.Exam;
// import com.example.campushub.exam.dto.ExamEditDto;
// import com.example.campushub.exam.dto.ExamFindAllResponse;
// import com.example.campushub.exam.dto.ExamScoreInputRequest;
// import com.example.campushub.exam.dto.ExamScoreUpdateResponse;
// import com.example.campushub.exam.repository.ExamRepository;
// import com.example.campushub.global.config.QueryDslConfig;
// import com.example.campushub.schoolyear.domain.SchoolYear;
// import com.example.campushub.schoolyear.domain.Semester;
// import com.example.campushub.schoolyear.repository.SchoolYearRepository;
// import com.example.campushub.user.domain.*;
// import com.example.campushub.user.dto.LoginUser;
// import com.example.campushub.user.repository.UserRepository;
// import com.example.campushub.usercourse.domain.UserCourse;
// import com.example.campushub.usercourse.repository.UserCourseRepository;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.context.annotation.Import;
// import static org.assertj.core.api.Assertions.assertThat;
// import java.time.LocalDate;
// import java.util.List;
//
// @SpringBootTest
// public class ExamServiceTest {
//
//     @Autowired
//     private ExamService examService;
//     @Autowired
//     private ExamRepository examRepository;
//     @Autowired
//     private UserRepository userRepository;
//     @Autowired
//     private DeptRepository deptRepository;
//     @Autowired
//     private SchoolYearRepository schoolYearRepository;
//     @Autowired
//     private UserCourseRepository userCourseRepository;
//
//     @AfterEach
//     public void tearDown() {
//         examRepository.deleteAll();
//         userCourseRepository.deleteAll();
//         userRepository.deleteAll();
//         deptRepository.deleteAll();
//         schoolYearRepository.deleteAll();
//     }
//
//     @Test
//     @DisplayName("과목별 학생 시험 점수 조회")
//     public void getExamScores() {
//         // given
//         Dept dept1 = deptRepository.save(createDept("컴소과"));
//
//         User student1 = userRepository.save(createStudent("김", "10", dept1));
//         LoginUser loginUser = createLoginUser(student1);
//
//         UserCourse userCourse1 = userCourseRepository.save(UserCourse.builder()
//                 .user(student1) // student1과 연결
//                 .build());
//
//         // userCourse1에 연결된 시험
//         examRepository.save(Exam.builder()
//                 .userCourse(userCourse1)
//                 .midScore(85)
//                 .finalScore(90)
//                 .totalScore(175)
//                 .build());
//
//         // when
//         List<ExamFindAllResponse> examScores = examService.getExamScores(loginUser, userCourse1.getId());
//
//         // then
//         assertThat(examScores).hasSize(1);
//         ExamFindAllResponse response = examScores.get(0);
//
//         assertThat(response.getUsername()).isEqualTo(student1.getUserName());  // 이름 비교
//         assertThat(response.getUserNum()).isEqualTo(student1.getUserNum());  // 학번 비교
//         assertThat(response.getDeptName()).isEqualTo(dept1.getDeptName());
//         assertThat(response.getMidScore()).isEqualTo(85);
//         assertThat(response.getFinalScore()).isEqualTo(90);
//         assertThat(response.getTotalScore()).isEqualTo(175);
//     }
//
//     @Test
//     @DisplayName("시험 점수 업데이트")
//     public void updateExamScore(){
//         //given
//         User professor = userRepository.save(createProfessor("0001"));
//         LoginUser loginUser = createLoginUser(professor);
//
//         Dept dept1 = deptRepository.save(createDept("컴소과"));
//         Dept dept2 = deptRepository.save(createDept("실디과"));
//
//         User student1 = userRepository.save(createStudent("김", "10", dept1));
//         User student2 = userRepository.save(createStudent("박", "20", dept2));
//         User student3 = userRepository.save(createStudent("최", "30", dept1));
//
//         Exam exam = examRepository.save(createExam(0, 0));
//         ExamScoreInputRequest request = ExamScoreInputRequest.builder()
//                 .examId(exam.getId())
//                 .midScore(85)
//                 .finalScore(90)
//                 .build();
//
//         //when
//         ExamScoreUpdateResponse response = examService.updateExamScore(loginUser, request);
//
//         //then
//         assertThat(response.getExamId()).isEqualTo(exam.getId());
//         assertThat(response.getMidScore()).isEqualTo(85);
//         assertThat(response.getFinalScore()).isEqualTo(90);
//         assertThat(response.getTotalScore()).isEqualTo(175);
//     }
//
//     @Test
//     @DisplayName("시험점수 수정")
//     public void editExamScore(){
//         //given
//         Dept dept = deptRepository.save(createDept("컴소과"));
//         User student = userRepository.save(createStudent("홍길동", "1234", dept));
//         User professor = userRepository.save(createProfessor("5678"));
//         LoginUser loginUser = createLoginUser(professor);
//
//         UserCourse userCourse = userCourseRepository.save(UserCourse.builder()
//                 .user(student)
//                 .build());
//
//         Exam exam = examRepository.save(Exam.builder()
//                 .userCourse(userCourse)
//                 .midScore(70)
//                 .finalScore(80)
//                 .totalScore(150)
//                 .build());
//
//         ExamEditDto editDto = new ExamEditDto(90,80);
//
//         // when
//         examService.editExam(exam.getId(), editDto);
//
//         // then
//         Exam updatedExam = examRepository.findById(exam.getId())
//                 .orElseThrow(() -> new RuntimeException("Exam not found"));
//
//         assertThat(updatedExam.getMidScore()).isEqualTo(90); // 중간 점수 검증
//         assertThat(updatedExam.getFinalScore()).isEqualTo(80); // 기말 점수 검증
//         assertThat(updatedExam.getTotalScore()).isEqualTo(170); // 총점 검증
//     }
//
//
//     private User createStudent(String name, String userNum, Dept dept) {
//         return User.builder()
//             .userName(name)
//             .userNum(userNum)
//             .password("1234")
//             .email("test@gmail.com")
//             .dept(dept) .phone("1234456567")
//             .role(Role.USER)
//             .grade(Grade.FIRST_GRADE)
//             .type(Type.STUDENT)
//             .status(Status.BREAK)
//             .build();
//     }
//
//     private User createProfessor(String userNum) {
//         return User.builder()
//                 .userName(" ")
//                 .userNum(userNum)
//                 .password("1234")
//                 .email("test@gmail.com")
//                 .phone("1234456567")
//                 .role(Role.USER)
//                 .type(Type.PROFESSOR)
//                 .build();
//     }
//
//     private LoginUser createLoginUser(User user) {
//         return LoginUser.builder()
//                 .userNum(user.getUserNum())
//                 .role(user.getRole())
//                 .type(user.getType())
//                 .build();
//     }
//
//     private SchoolYear createSchoolYear() {
//         return SchoolYear.builder()
//                 .year(LocalDate.now())
//                 .semester(Semester.first_semester)
//                 .is_current(true)
//                 .build();
//     }
//     private Exam createExam(int midScore, int finalScore) {
//         return Exam.builder()
//                 .midScore(midScore)
//                 .finalScore(finalScore)
//                 .totalScore(midScore+finalScore)
//                 .build();
//     }
//
//     private Dept createDept(String deptName) {
//         return Dept.builder()
//                 .deptName(deptName)
//                 .build();
//     }
// }

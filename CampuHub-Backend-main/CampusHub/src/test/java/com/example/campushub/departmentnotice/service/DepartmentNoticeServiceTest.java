//package com.example.campushub.departmentnotice.service;
//
//import com.example.campushub.departmentnotice.domain.DepartmentNotice;
//import com.example.campushub.departmentnotice.dto.DepartmentNoticeRequestDto;
//import com.example.campushub.departmentnotice.dto.DepartmentNoticeUpdateRequestDto;
//import com.example.campushub.departmentnotice.repository.DepartmentNoticeRepository;
//import com.example.campushub.departmentnotice.dto.DepartmentNoticeSearchCondition;
//import com.example.campushub.global.config.QueryDslConfig;
//import com.example.campushub.user.domain.Role;
//import com.example.campushub.user.domain.Type;
//import com.example.campushub.user.domain.User;
//import com.example.campushub.user.dto.LoginUser;
//import com.example.campushub.user.repository.UserRepository;
//import com.example.campushub.usercourse.domain.UserCourse;
//import com.example.campushub.usercourse.dto.UserCourseResponseDto;
//import com.example.campushub.usercourse.repository.UserCourseRepository;
//import com.example.campushub.course.domain.Course;
//import com.example.campushub.course.repository.CourseRepository;
//import com.example.campushub.dept.domain.Dept;
//import com.example.campushub.dept.repository.DeptRepository;
//import jakarta.persistence.EntityNotFoundException;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.access.AccessDeniedException;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@DataJpaTest
//@Slf4j
//@Import({DepartmentNoticeService.class, QueryDslConfig.class})
//class DepartmentNoticeServiceTest {
//
//    @Autowired
//    private DepartmentNoticeService departmentNoticeService;
//
//    @Autowired
//    private DepartmentNoticeRepository departmentNoticeRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private UserCourseRepository userCourseRepository;
//
//    @Autowired
//    private CourseRepository courseRepository;
//
//    @Autowired
//    private DeptRepository deptRepository;
//
//    @AfterEach
//    void tearDown() {
//        log.info("테스트 데이터 정리 중...");
//        departmentNoticeRepository.deleteAll();
//        userCourseRepository.deleteAll();
//        courseRepository.deleteAll();
//        userRepository.deleteAll();
//        deptRepository.deleteAll();
//        log.info("테스트 데이터 정리 완료.");
//    }
//
//    @Test
//    @DisplayName("학과 공지사항 작성 - 성공")
//    void createDepartmentNotice_Success() {
//        // Given
//        Dept dept = createDept("컴퓨터공학과");
//        User professor = createProfessor("300001", "홍길동", dept);
//        Course course = createCourse(professor, "운영체제");
//        UserCourse userCourse = createUserCourse(course, professor);
//
//        DepartmentNoticeRequestDto requestDto = DepartmentNoticeRequestDto.builder()
//                .title("공지사항 제목")
//                .content("공지사항 내용")
//                .userCourseId(userCourse.getId())
//                .build();
//
//        LoginUser loginUser = LoginUser.builder()
//                .userNum(professor.getUserNum())
//                .role(professor.getRole())
//                .type(professor.getType())
//                .build();
//
//        log.info("테스트 데이터 준비 완료: 교수={}, 강의={}, UserCourse={}", professor, course, userCourse);
//
//        // When
//        departmentNoticeService.createDepartmentNotice(requestDto, loginUser);
//
//        // Then
//        List<DepartmentNotice> notices = departmentNoticeRepository.findAll();
//        assertThat(notices).hasSize(1); // 공지사항이 하나 저장되었는지 확인
//        DepartmentNotice savedNotice = notices.get(0);
//        assertThat(savedNotice.getTitle()).isEqualTo("공지사항 제목");
//        assertThat(savedNotice.getContent()).isEqualTo("공지사항 내용");
//        assertThat(savedNotice.getUserCourse().getId()).isEqualTo(userCourse.getId());
//        assertThat(savedNotice.getAuthor().getUserNum()).isEqualTo(professor.getUserNum());
//    }
//
//    @Test
//    @DisplayName("학과 공지사항 작성 - 실패 (권한 부족)")
//    void createDepartmentNotice_Failure_Unauthorized() {
//        // Given
//        Dept dept = createDept("컴퓨터공학과");
//        User student = createStudent("300002", "김철수", dept);
//        Course course = createCourse(student, "데이터베이스");
//        UserCourse userCourse = createUserCourse(course, student);
//
//        DepartmentNoticeRequestDto requestDto = DepartmentNoticeRequestDto.builder()
//                .title("공지사항 제목")
//                .content("공지사항 내용")
//                .userCourseId(userCourse.getId())
//                .build();
//
//        LoginUser loginUser = LoginUser.builder()
//                .userNum(student.getUserNum())
//                .role(student.getRole())
//                .type(student.getType())
//                .build();
//
//        log.info("테스트 데이터 준비 완료: 학생={}, 강의={}, UserCourse={}", student, course, userCourse);
//
//        // When & Then
//        assertThrows(AccessDeniedException.class, () -> departmentNoticeService.createDepartmentNotice(requestDto, loginUser));
//    }
//
//
//    @Test
//    @DisplayName("학과 공지사항 작성 - 실패 (존재하지 않는 UserCourse)")
//    void createDepartmentNotice_Failure_InvalidUserCourse() {
//        // Given
//        Dept dept = createDept("컴퓨터공학과");
//        User professor = createProfessor("300001", "홍길동", dept);
//
//        DepartmentNoticeRequestDto requestDto = DepartmentNoticeRequestDto.builder()
//                .title("공지사항 제목")
//                .content("공지사항 내용")
//                .userCourseId(999L) // 존재하지 않는 UserCourse ID
//                .build();
//
//        LoginUser loginUser = LoginUser.builder()
//                .userNum(professor.getUserNum())
//                .role(professor.getRole())
//                .type(professor.getType())
//                .build();
//
//        log.info("테스트 데이터 준비 완료: 교수={}", professor);
//
//        // When & Then
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
//                () -> departmentNoticeService.createDepartmentNotice(requestDto, loginUser));
//
//        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 UserCourse ID입니다.");
//    }
//
//    @Test
//    @DisplayName("학과 공지사항 작성 - 실패 (로그인한 유저가 강의 개설자가 아님)")
//    void createDepartmentNotice_Failure_NotCourseCreator() {
//        // Given
//        Dept dept = createDept("컴퓨터공학과");
//        User professor1 = createProfessor("300001", "홍길동", dept);
//        User professor2 = createProfessor("300002", "이몽룡", dept);
//        Course course = createCourse(professor1, "운영체제");
//        UserCourse userCourse = createUserCourse(course, professor1);
//
//        DepartmentNoticeRequestDto requestDto = DepartmentNoticeRequestDto.builder()
//                .title("공지사항 제목")
//                .content("공지사항 내용")
//                .userCourseId(userCourse.getId())
//                .build();
//
//        LoginUser loginUser = LoginUser.builder()
//                .userNum(professor2.getUserNum()) // 다른 교수로 로그인
//                .role(professor2.getRole())
//                .type(professor2.getType())
//                .build();
//
//        log.info("테스트 데이터 준비 완료: 강의 개설자={}, 로그인한 유저={}", professor1, professor2);
//
//        // When & Then
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
//                () -> departmentNoticeService.createDepartmentNotice(requestDto, loginUser));
//
//        assertThat(exception.getMessage()).isEqualTo("해당 강의의 공지사항을 작성할 권한이 없습니다.");
//    }
//
//    @Test
//    @DisplayName("본인이 개설한 강의 목록 조회 - 성공")
//    void getCoursesByProfessor_Success() {
//        // Given
//        Dept dept = createDept("컴퓨터공학과");
//
//        // 교수 A 생성 및 강의 추가
//        User professorA = createProfessor("300001", "홍길동", dept);
//        Course course1 = createCourse(professorA, "운영체제");
//        Course course2 = createCourse(professorA, "자료구조");
//        createUserCourse(course1, professorA);
//        createUserCourse(course2, professorA);
//
//        // 교수 B 생성 및 강의 추가
//        User professorB = createProfessor("300002", "이몽룡", dept);
//        Course course3 = createCourse(professorB, "알고리즘");
//        createUserCourse(course3, professorB);
//
//        // 로그인 정보
//        LoginUser loginUser = LoginUser.builder()
//                .userNum(professorA.getUserNum())
//                .role(professorA.getRole())
//                .type(professorA.getType())
//                .build();
//
//        log.info("테스트 데이터 준비 완료: 교수 A={}, 강의=[{}, {}], 교수 B={}, 강의=[{}]",
//                professorA.getUserName(), course1.getCourseName(), course2.getCourseName(),
//                professorB.getUserName(), course3.getCourseName());
//
//        // When
//        List<UserCourseResponseDto> courses = departmentNoticeService.getCoursesByProfessor(loginUser.getUserNum());
//
//        // Then
//        assertThat(courses).hasSize(2); // 교수 A가 개설한 강의 2개 확인
//        assertThat(courses).extracting("courseName")
//                .containsExactlyInAnyOrder("운영체제", "자료구조");
//    }
//
//    @Test
//    @DisplayName("학과 공지사항 수정 - 성공")
//    void updateDepartmentNotice_Success() {
//        // Given
//        Dept dept = createDept("컴퓨터공학과");
//        User professor = createProfessor("300001", "홍길동", dept);
//        Course course = createCourse(professor, "운영체제");
//        UserCourse userCourse = createUserCourse(course, professor);
//
//        DepartmentNotice notice = createDepartmentNotice("공지사항 제목", "공지사항 내용", professor, userCourse);
//
//        DepartmentNoticeUpdateRequestDto updateRequestDto = DepartmentNoticeUpdateRequestDto.builder()
//                .id(notice.getId())
//                .title("수정된 제목")
//                .content("수정된 내용")
//                .build();
//
//        LoginUser loginUser = LoginUser.builder()
//                .userNum(professor.getUserNum())
//                .role(professor.getRole())
//                .type(professor.getType())
//                .build();
//
//        log.info("테스트 데이터 준비 완료: 교수={}, 강의={}, 공지사항={}", professor, course, notice);
//
//        // When
//        departmentNoticeService.updateDepartmentNotice(updateRequestDto, loginUser);
//
//        // Then
//        DepartmentNotice updatedNotice = departmentNoticeRepository.findById(notice.getId())
//                .orElseThrow(() -> new EntityNotFoundException("공지사항이 존재하지 않습니다."));
//        assertThat(updatedNotice.getTitle()).isEqualTo("수정된 제목");
//        assertThat(updatedNotice.getContent()).isEqualTo("수정된 내용");
//    }
//
//    @Test
//    @DisplayName("학과 공지사항 수정 - 실패 (작성자 아님)")
//    void updateDepartmentNotice_Failure_NotAuthor() {
//        // Given
//        Dept dept = createDept("컴퓨터공학과");
//        User professor = createProfessor("300001", "홍길동", dept);
//        User anotherProfessor = createProfessor("300002", "이몽룡", dept);
//        Course course = createCourse(professor, "운영체제");
//        UserCourse userCourse = createUserCourse(course, professor);
//
//        DepartmentNotice notice = createDepartmentNotice("공지사항 제목", "공지사항 내용", professor, userCourse);
//
//        DepartmentNoticeUpdateRequestDto updateRequestDto = DepartmentNoticeUpdateRequestDto.builder()
//                .id(notice.getId())
//                .title("수정된 제목")
//                .content("수정된 내용")
//                .build();
//
//        LoginUser loginUser = LoginUser.builder()
//                .userNum(anotherProfessor.getUserNum())
//                .role(anotherProfessor.getRole())
//                .type(anotherProfessor.getType())
//                .build();
//
//        log.info("테스트 데이터 준비 완료: 교수={}, 다른 교수={}, 강의={}, 공지사항={}", professor, anotherProfessor, course, notice);
//
//        // When & Then
//        assertThrows(AccessDeniedException.class, () -> departmentNoticeService.updateDepartmentNotice(updateRequestDto, loginUser));
//    }
//
//    @Test
//    @DisplayName("학과 공지사항 삭제 - 성공")
//    void deleteDepartmentNotice_Success() {
//        // Given
//        Dept dept = createDept("컴퓨터공학과");
//        User professor = createProfessor("300001", "홍길동", dept);
//        Course course = createCourse(professor, "운영체제");
//        UserCourse userCourse = createUserCourse(course, professor);
//
//        DepartmentNotice notice = createDepartmentNotice("공지사항 제목", "공지사항 내용", professor, userCourse);
//
//        LoginUser loginUser = LoginUser.builder()
//                .userNum(professor.getUserNum())
//                .role(professor.getRole())
//                .type(professor.getType())
//                .build();
//
//        log.info("테스트 데이터 준비 완료: 교수={}, 강의={}, 공지사항={}", professor, course, notice);
//
//        // When
//        departmentNoticeService.deleteDepartmentNotice(notice.getId(), loginUser);
//
//        // Then
//        List<DepartmentNotice> notices = departmentNoticeRepository.findAll();
//        assertThat(notices).isEmpty(); // 공지사항이 삭제되었는지 확인
//    }
//
//    @Test
//    @DisplayName("학과 공지사항 삭제 - 실패 (작성자가 아님)")
//    void deleteDepartmentNotice_Failure_NotAuthor() {
//        // Given
//        Dept dept = createDept("컴퓨터공학과");
//        User professor = createProfessor("300001", "홍길동", dept);
//        User anotherProfessor = createProfessor("300002", "이몽룡", dept);
//        Course course = createCourse(professor, "운영체제");
//        UserCourse userCourse = createUserCourse(course, professor);
//
//        DepartmentNotice notice = createDepartmentNotice("공지사항 제목", "공지사항 내용", professor, userCourse);
//
//        LoginUser loginUser = LoginUser.builder()
//                .userNum(anotherProfessor.getUserNum()) // 다른 교수로 로그인
//                .role(anotherProfessor.getRole())
//                .type(anotherProfessor.getType())
//                .build();
//
//        log.info("테스트 데이터 준비 완료: 작성자={}, 삭제 시도 유저={}, 공지사항={}", professor, anotherProfessor, notice);
//
//        // When & Then
//        assertThrows(AccessDeniedException.class,
//                () -> departmentNoticeService.deleteDepartmentNotice(notice.getId(), loginUser));
//    }
//
//    @Test
//    @DisplayName("학과 공지사항 삭제 - 실패 (존재하지 않는 공지사항)")
//    void deleteDepartmentNotice_Failure_NotFound() {
//        // Given
//        Dept dept = createDept("컴퓨터공학과");
//        User professor = createProfessor("300001", "홍길동", dept);
//
//        LoginUser loginUser = LoginUser.builder()
//                .userNum(professor.getUserNum())
//                .role(professor.getRole())
//                .type(professor.getType())
//                .build();
//
//        log.info("테스트 데이터 준비 완료: 교수={}", professor);
//
//        // When & Then
//        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
//                () -> departmentNoticeService.deleteDepartmentNotice(999L, loginUser)); // 존재하지 않는 ID
//        assertThat(exception.getMessage()).isEqualTo("공지사항을 찾을 수 없습니다.");
//    }
//
//
//    // 데이터 생성 메서드
//    private Dept createDept(String name) {
//        Dept dept = Dept.builder()
//                .deptName(name)
//                .build();
//        return deptRepository.save(dept);
//    }
//
//    private User createProfessor(String userNum, String userName, Dept dept) {
//        User professor = User.builder()
//                .userNum(userNum)
//                .userName(userName)
//                .type(Type.PROFESSOR)
//                .dept(dept)
//                .password("1234")
//                .email(userName + "@campushub.com")
//                .role(Role.USER)
//                .build();
//        return userRepository.save(professor);
//    }
//
//    private User createStudent(String userNum, String userName, Dept dept) {
//        User student = User.builder()
//                .userNum(userNum)
//                .userName(userName)
//                .type(Type.STUDENT)
//                .dept(dept)
//                .password("1234")
//                .email(userName + "@campushub.com")
//                .role(Role.USER)
//                .build();
//        return userRepository.save(student);
//    }
//
//    private Course createCourse(User professor, String courseName) {
//        Course course = Course.builder()
//                .courseName(courseName)
//                .user(professor)
//                .build();
//        return courseRepository.save(course);
//    }
//
//    private UserCourse createUserCourse(Course course, User user) {
//        UserCourse userCourse = UserCourse.builder()
//                .course(course)
//                .user(user)
//                .build();
//        return userCourseRepository.save(userCourse);
//    }
//
//    private DepartmentNotice createDepartmentNotice(String title, String content, User author, UserCourse userCourse) {
//        DepartmentNotice notice = DepartmentNotice.builder()
//                .title(title)
//                .content(content)
//                .author(author)
//                .userCourse(userCourse)
//                .build();
//        return departmentNoticeRepository.save(notice);
//    }
//}

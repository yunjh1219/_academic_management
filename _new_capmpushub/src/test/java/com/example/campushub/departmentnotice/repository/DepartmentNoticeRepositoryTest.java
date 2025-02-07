//package com.example.campushub.departmentnotice.repository;
//
//import com.example.campushub.course.domain.Course;
//import com.example.campushub.course.repository.CourseRepository;
//import com.example.campushub.departmentnotice.domain.DepartmentNotice;
//import com.example.campushub.departmentnotice.dto.DepartmentNoticeSearchCondition;
//import com.example.campushub.dept.domain.Dept;
//import com.example.campushub.dept.repository.DeptRepository;
//import com.example.campushub.global.config.QueryDslConfig;
//import com.example.campushub.notice.domain.Notice;
//import com.example.campushub.departmentnotice.dto.DepartmentNoticeResponseDto;
//import com.example.campushub.notice.dto.NoticeSearchCondition;
//import com.example.campushub.notice.repository.NoticeRepository;
//import com.example.campushub.user.domain.Role;
//import com.example.campushub.user.domain.Type;
//import com.example.campushub.user.domain.User;
//import com.example.campushub.user.repository.UserRepository;
//import com.example.campushub.usercourse.domain.UserCourse;
//import com.example.campushub.usercourse.repository.UserCourseRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//
//import java.time.LocalDateTime;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@Slf4j
//@Import(QueryDslConfig.class)
//class DepartmentNoticeRepositoryTest {
//
//    @Autowired
//    private DepartmentNoticeRepository departmentNoticeRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserCourseRepository userCourseRepository;
//    @Autowired
//    private DeptRepository deptRepository;
//    @Autowired
//    private CourseRepository courseRepository;
//
//    @AfterEach
//    void tearDown() {
//        log.info("테스트 데이터 정리 중...");
//        try {
//            departmentNoticeRepository.deleteAll(); // 공지사항 삭제
//            userCourseRepository.deleteAll();      // UserCourse 삭제
//            courseRepository.deleteAll();          // Course 삭제
//            userRepository.deleteAll();            // User 삭제
//            deptRepository.deleteAll();            // Dept 삭제
//        } catch (Exception e) {
//            log.error("데이터 삭제 중 오류 발생: {}", e.getMessage(), e);
//        }
//        log.info("테스트 데이터 정리 완료.");
//    }
//
//    @Test
//    @DisplayName("학과 공지사항 조회 - 검색어 없음")
//    void findNoticesForUser_NoKeyword() {
//        // Given
//        Dept dept = createDept("컴퓨터공학과");
//        User professor = createProfessor("300002", "이몽룡", dept);
//        Course course = createCourse(professor, "알고리즘");
//        UserCourse userCourse = createUserCourse(course, professor);
//        createDepartmentNoticesForUser(userCourse, "기말고사 공지", "기말고사 일정");
//
//        // 검색 조건: 검색어 없음
//        String userNum = professor.getUserNum();
//        DepartmentNoticeSearchCondition condition = DepartmentNoticeSearchCondition.builder()
//                .filter("title") // 필터는 설정하지만
//                .keyword(null)   // 검색어는 null
//                .build();
//        PageRequest pageable = PageRequest.of(0, 10);
//
//        log.info("테스트 데이터 준비 완료: 교수={}, 학과={}, 강의={}, 조건={}", professor, dept, course, condition);
//
//        // When
//        Page<DepartmentNoticeResponseDto> result = departmentNoticeRepository.findDepartmentNoticesForUser(userNum, condition, pageable);
//
//        // Then
//        log.info("조회된 결과: 총 {}건", result.getTotalElements());
//        result.getContent().forEach(dto ->
//                log.info("조회된 공지사항: ID={}, 제목={}, 작성자={}, 강의명={}",
//                        dto.getId(), dto.getTitle(), dto.getProfessorName(), dto.getCourseName())
//        );
//
//        assertThat(result.getContent()).hasSize(10); // 페이징 크기 확인
//        DepartmentNoticeResponseDto dto = result.getContent().get(0);
//        assertThat(dto.getProfessorName()).isEqualTo("이몽룡");
//        assertThat(dto.getCourseName()).isEqualTo("알고리즘");
//    }
//
//
//    @Test
//    @DisplayName("학과 공지사항 조회 - 필터 조건 : 제목")
//    void findNoticesForUser_FilterByTitle() {
//        // Given
//        Dept dept = createDept("컴퓨터공학과");
//        User professor = createProfessor("300001", "홍길동", dept);
//        Course course = createCourse(professor, "자료구조");
//        UserCourse userCourse = createUserCourse(course, professor);
//
//        // 공지사항 생성
//        createDepartmentNotice(professor, userCourse, "중간고사 공지", "중간고사 일정");
//        createDepartmentNotice(professor, userCourse, "기말고사 공지", "기말고사 일정");
//
//        // 검색 조건: 제목에 "중간고사" 포함
//        String userNum = professor.getUserNum();
//        DepartmentNoticeSearchCondition condition = DepartmentNoticeSearchCondition.builder()
//                .filter("title") // 제목 필터
//                .keyword("중간고사") // "중간고사" 검색
//                .build();
//        PageRequest pageable = PageRequest.of(0, 10);
//
//        log.info("테스트 데이터 준비 완료: 교수={}, 학과={}, 강의={}, 조건={}", professor, dept, course, condition);
//
//        // When
//        Page<DepartmentNoticeResponseDto> result = departmentNoticeRepository.findDepartmentNoticesForUser(userNum, condition, pageable);
//
//        // Then
//        log.info("조회된 결과: 총 {}건", result.getTotalElements());
//        result.getContent().forEach(dto ->
//                log.info("조회된 공지사항: ID={}, 제목={}, 작성자={}, 강의명={}",
//                        dto.getId(), dto.getTitle(), dto.getProfessorName(), dto.getCourseName())
//        );
//
//        // 검증
//        assertThat(result.getContent()).hasSize(1); // "중간고사 공지"만 포함
//        DepartmentNoticeResponseDto dto = result.getContent().get(0);
//        assertThat(dto.getTitle()).isEqualTo("중간고사 공지");
//        assertThat(dto.getProfessorName()).isEqualTo("홍길동");
//        assertThat(dto.getCourseName()).isEqualTo("자료구조");
//    }
//
//    @Test
//    @DisplayName("학과 공지사항 조회 - 필터 조건 : 작성자")
//    void findNoticesForUser_FilterByCreatedBy() {
//        // Given
//        // 학과 생성
//        Dept dept = createDept("컴퓨터공학과");
//
//        // 공지사항의 주 작성자
//        User professor = createProfessor("300003", "임꺽정", dept);
//        Course course = createCourse(professor, "데이터베이스");
//        UserCourse userCourse = createUserCourse(course, professor);
//        createDepartmentNoticesForUser(userCourse, "강의 변경 공지", "강의 시간 변경");
//
//        // 다른 교수님 생성 및 공지사항 생성
//        User otherProfessor = createProfessor("300004", "홍길동", dept);
//        Course otherCourse = createCourse(otherProfessor, "운영체제");
//        UserCourse otherUserCourse = createUserCourse(otherCourse, otherProfessor);
//        createDepartmentNoticesForUser(otherUserCourse, "운영체제 공지", "운영체제 강의 변경");
//
//        // 검색 조건: 작성자로 필터링 (임꺽정 교수님)
//        String userNum = professor.getUserNum();
//        DepartmentNoticeSearchCondition condition = DepartmentNoticeSearchCondition.builder()
//                .filter("createdBy")  // 작성자로 필터링
//                .keyword("임꺽정")    // 작성자 이름
//                .build();
//        PageRequest pageable = PageRequest.of(0, 10);
//
//        log.info("테스트 데이터 준비 완료: 교수={}, 학과={}, 강의={}, 조건={}", professor, dept, course, condition);
//
//        // When
//        Page<DepartmentNoticeResponseDto> result = departmentNoticeRepository.findDepartmentNoticesForUser(userNum, condition, pageable);
//
//        // Then
//        log.info("조회된 결과: 총 {}건", result.getTotalElements());
//        result.getContent().forEach(dto ->
//                log.info("조회된 공지사항: ID={}, 제목={}, 작성자={}, 강의명={}",
//                        dto.getId(), dto.getTitle(), dto.getProfessorName(), dto.getCourseName())
//        );
//
//        // 검증
//        assertThat(result.getContent()).hasSize(10); // 페이징 크기 확인
//        DepartmentNoticeResponseDto dto = result.getContent().get(0);
//        assertThat(dto.getProfessorName()).isEqualTo("임꺽정");
//        assertThat(dto.getCourseName()).isEqualTo("데이터베이스");
//    }
//
//
//    @Test
//    @DisplayName("학과 공지사항 조회 - 필터 조건 : 강의명")
//    void findNoticesForUser_FilterByCourseName() {
//        // Given
//        // 학과 생성
//        Dept dept = createDept("컴퓨터공학과");
//
//        // 교수 A 생성 및 강의 생성
//        User professorA = createProfessor("300004", "홍길동", dept);
//        Course courseA = createCourse(professorA, "운영체제");
//        UserCourse userCourseA = createUserCourse(courseA, professorA);
//
//        // 공지사항 생성 (운영체제 강의)
//        createDepartmentNoticesForUser(userCourseA, "운영체제 공지", "운영체제 강의 안내");
//
//        // 교수 B 생성 및 강의 생성
//        User professorB = createProfessor("300005", "이몽룡", dept);
//        Course courseB = createCourse(professorB, "데이터베이스");
//        UserCourse userCourseB = createUserCourse(courseB, professorB);
//
//        // 공지사항 생성 (데이터베이스 강의)
//        createDepartmentNoticesForUser(userCourseB, "데이터베이스 공지", "데이터베이스 강의 안내");
//
//        // 검색 조건: 강의명으로 필터링 (운영체제)
//        String userNum = professorA.getUserNum();
//        DepartmentNoticeSearchCondition condition = DepartmentNoticeSearchCondition.builder()
//                .filter("courseName")  // 강의명으로 필터링
//                .keyword("운영체제")   // 강의명
//                .build();
//        PageRequest pageable = PageRequest.of(0, 10);
//
//        log.info("테스트 데이터 준비 완료: 교수 A={}, 강의 A={}, 교수 B={}, 강의 B={}, 조건={}",
//                professorA, courseA, professorB, courseB, condition);
//
//        // When
//        Page<DepartmentNoticeResponseDto> result = departmentNoticeRepository.findDepartmentNoticesForUser(userNum, condition, pageable);
//
//        // Then
//        log.info("조회된 결과: 총 {}건", result.getTotalElements());
//        result.getContent().forEach(dto ->
//                log.info("조회된 공지사항: ID={}, 제목={}, 작성자={}, 강의명={}",
//                        dto.getId(), dto.getTitle(), dto.getProfessorName(), dto.getCourseName())
//        );
//
//        // 검증
//        assertThat(result.getContent()).hasSize(10); // 페이징 크기 확인
//        result.getContent().forEach(dto -> {
//            assertThat(dto.getProfessorName()).isEqualTo("홍길동");
//            assertThat(dto.getCourseName()).isEqualTo("운영체제");
//        });
//    }
//
//    @Test
//    @DisplayName("학과 공지사항 세부 조회")
//    void getDepartmentNoticeById_Success() {
//        // Given
//        Dept dept = createDept("컴퓨터공학과");
//        User professor = createProfessor("300001", "홍길동", dept);
//        Course course = createCourse(professor, "자료구조");
//        UserCourse userCourse = createUserCourse(course, professor);
//
//        DepartmentNotice notice = createDepartmentNotice(professor, userCourse, "중간고사 공지", "중간고사 일정");
//
//        // When
//        DepartmentNoticeResponseDto result = departmentNoticeRepository.findByIdAndUser(notice.getId(), professor.getUserNum())
//                .orElseThrow(() -> new IllegalStateException("공지사항 조회 실패"));
//
//        // Then
//        assertThat(result.getTitle()).isEqualTo("중간고사 공지");
//        assertThat(result.getContent()).isEqualTo("중간고사 일정");
//        assertThat(result.getProfessorName()).isEqualTo("홍길동");
//        assertThat(result.getCourseName()).isEqualTo("자료구조");
//    }
//
//
//    private Dept createDept(String name) {
//        Dept dept = Dept.builder()
//                .deptName(name)
//                .build();
//        log.info("학과 생성 중: {}", dept);
//        return deptRepository.save(dept);
//    }
//
//    private User createProfessor(String userNum, String userName, Dept dept) {
//        User professor = User.builder()
//                .userName(userName)
//                .userNum(userNum)
//                .password("1234")
//                .email("professor@campushub.com")
//                .role(Role.USER)
//                .type(Type.PROFESSOR)
//                .dept(dept)
//                .build();
//
//        log.info("교수 생성 중: {}, 소속 학과: {}", professor, dept.getDeptName());
//        return userRepository.save(professor);
//    }
//
//    private Course createCourse(User professor, String courseName) {
//        // 강의 생성 및 저장
//        Course course = Course.builder()
//                .courseName(courseName)
//                .user(professor) // 교수와 연결
//                .build();
//        course = courseRepository.save(course); // 강의 저장
//        log.info("강의 저장 완료: 강의명={}, 강의 ID={}, 교수={}", course.getCourseName(), course.getId(), professor.getUserName());
//
//        return course;
//    }
//
//    private UserCourse createUserCourse(Course course, User user) {
//        // UserCourse 생성 및 저장
//        UserCourse userCourse = UserCourse.builder()
//                .user(user)
//                .course(course) // 강의와 연결
//                .build();
//        userCourseRepository.save(userCourse); // UserCourse 저장
//        log.info("UserCourse 저장 완료: 교수={}, 강의명={}, 강의 ID={}", user.getUserName(), course.getCourseName(), course.getId());
//
//        return userCourse;
//    }
//
//    private DepartmentNotice createDepartmentNotice(User user, UserCourse userCourse, String title, String content) {
//        DepartmentNotice notice = DepartmentNotice.builder()
//                .author(user) // 공지 작성자
//                .userCourse(userCourse) // 관련 강의(UserCourse)
//                .title(title)
//                .content(content)
//                .build();
//
//        log.info("학과 공지사항 생성: 작성자={}, 강의={}, 제목={}, 내용={}", user.getUserName(), userCourse.getCourse().getCourseName(), title, content);
//        return departmentNoticeRepository.save(notice);
//    }
//
//    private void createDepartmentNoticesForUser(UserCourse userCourse, String titlePrefix, String contentPrefix) {
//        for (int i = 1; i <= 20; i++) {
//            DepartmentNotice notice = createDepartmentNotice(userCourse.getUser(), userCourse, titlePrefix + " " + i, contentPrefix + " " + i);
//            departmentNoticeRepository.save(notice);
//            log.info("학과 공지사항 생성 완료: {}", notice);
//        }
//    }
//
//}

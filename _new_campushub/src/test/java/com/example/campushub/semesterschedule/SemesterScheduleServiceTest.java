//package com.example.campushub.semesterschedule;
//
//
//import com.example.campushub.schoolyear.domain.SchoolYear;
//import com.example.campushub.schoolyear.domain.Semester;
//import com.example.campushub.schoolyear.repository.SchoolYearRepository;
//import com.example.campushub.semsterschedule.domain.Schedule;
//import com.example.campushub.semsterschedule.domain.SemesterSchedule;
//import com.example.campushub.semsterschedule.dto.SemesterScheduleRequest;
//import com.example.campushub.semsterschedule.dto.SemesterScheduleResponse;
//import com.example.campushub.semsterschedule.repository.SemesterScheduleRepository;
//import com.example.campushub.semsterschedule.service.SemesterScheduleService;
//import com.example.campushub.user.domain.*;
//import com.example.campushub.user.dto.LoginUser;
//import com.example.campushub.user.repository.UserRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class SemesterScheduleServiceTest {
//
//    @Autowired
//    private SemesterScheduleService semesterScheduleService;
//    @Autowired
//    private SemesterScheduleRepository semesterScheduleRepository;
//    @Autowired
//    private SchoolYearRepository schoolYearRepository;
//    @Autowired
//    private UserRepository userRepository;
//
//    @AfterEach
//    public void tearDown() {
//        semesterScheduleRepository.deleteAllInBatch();
//        schoolYearRepository.deleteAllInBatch();
//    }
//
//    @Test
//    @DisplayName("학사 일정 추가")
//    @Transactional
//    public void createSemesterSchedule() {
//        //given
//        User user =createAdmin("1234");
//        LoginUser admin = createLoginUser(user);
//
//
//        SchoolYear schoolYear = schoolYearRepository.save(createSchoolYear(Semester.first_semester));
//
//        SemesterScheduleRequest request = SemesterScheduleRequest.builder()
//                .schedule(Schedule.COURSE_APPLY)
//                .startDate(LocalDateTime.of(2025, 1, 1, 9, 0))
//                .endDate(LocalDateTime.of(2025, 1, 1, 18, 0))
//                .dateCheck(false)
//                .eventName("수강 신청")
//                .build();
//
//
//        //when
//        semesterScheduleService.createSemesterSchedule(request,admin);
//
//        //then
//        List<SemesterSchedule> schedules = semesterScheduleRepository.findAll();
//        assertThat(schedules).hasSize(1);
//        assertThat(schedules.get(0).getEventName()).isEqualTo("수강 신청");
//
//
//    }
//
//    @Test
//    @DisplayName("특정 월의 학사 일정 조회")
//    @Transactional
//    public void getSchedulesByMonth() {
//
//        //given
//        User user =userRepository.save(createUser("강","1234"));
//        LoginUser admin = createLoginUser(user);
//        SchoolYear schoolYear = schoolYearRepository.save(createSchoolYear(Semester.first_semester));
//
//        semesterScheduleRepository.save(createSchedule("수강 신청", LocalDateTime.of(2025, 1, 1, 9, 0), LocalDateTime.of(2025, 1, 1, 18, 0), Schedule.COURSE_APPLY));
//        semesterScheduleRepository.save(createSchedule("등록금 납부", LocalDateTime.of(2025, 1, 1, 9, 0), LocalDateTime.of(2025, 1, 1, 18, 0), Schedule.COURSE_APPLY));
//        semesterScheduleRepository.save(createSchedule("졸업식", LocalDateTime.of(2025, 1, 1, 9, 0), LocalDateTime.of(2025, 1, 1, 18, 0), Schedule.COURSE_APPLY));
//
//        //when
//        List<SemesterScheduleResponse> schedules = semesterScheduleService.getSchedulesByDate(LocalDateTime.of(2025, 1, 1, 12, 0),admin);
//        //then
//        assertThat(schedules).hasSize(3);
//        assertThat(schedules.get(0).getEventName()).isEqualTo("수강 신청");
//        assertThat(schedules.get(1).getEventName()).isEqualTo("등록금 납부");
//
//
//    }
//
//    @Test
//    @DisplayName("특정 일의 학사 일정 조회 ")
//    @Transactional
//    public void getSchedulesByDate() {
//        //given
//        User user =userRepository.save(createUser("강","1234"));
//        LoginUser admin = createLoginUser(user);
//        semesterScheduleRepository.save(createSchedule("수강 신청", LocalDateTime.of(2025, 1, 1, 9, 0), LocalDateTime.of(2025, 1, 1, 18, 0), Schedule.COURSE_APPLY));
//        semesterScheduleRepository.save(createSchedule("등록금 납부", LocalDateTime.of(2025, 1, 1, 10, 0), LocalDateTime.of(2025, 1, 1, 15, 0), Schedule.COURSE_APPLY));
//        semesterScheduleRepository.save(createSchedule("졸업식", LocalDateTime.of(2025, 1, 2, 10, 0), LocalDateTime.of(2025, 1, 2, 12, 0), Schedule.COURSE_APPLY));
//
//        //when
//        List<SemesterScheduleResponse> schedules = semesterScheduleService.getSchedulesByDate(LocalDateTime.of(2025, 1, 1, 12, 0),admin);
//
//        //then
//        assertThat(schedules).hasSize(2);
//        assertThat(schedules.get(0).getEventName()).isEqualTo("수강 신청");
//        assertThat(schedules.get(1).getEventName()).isEqualTo("등록금 납부");
//    }
//
//
//        private SemesterSchedule createSchedule(String eventName, LocalDateTime startDate, LocalDateTime endDate, Schedule scheduleType) {
//        return SemesterSchedule.builder()
//                .eventName(eventName)
//                .startDate(startDate)
//                .endDate(endDate)
//                .schedule(scheduleType)
//                .build();
//    }
//
//    //학년도 추가 메서드
//    private SchoolYear createSchoolYear(Semester semester) {
//        return SchoolYear.builder()
//                .year(LocalDate.now())
//                .semester(semester)
//                .build();
//
//    }
//
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
//
//    private LoginUser createLoginUser(User user) {
//        return LoginUser.builder()
//                .userNum(user.getUserNum())
//                .role(Role.ADMIN)
//                .type(Type.ADMIN)
//                .build();
//    }
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
//
//}

package com.home._ac_front.assign;


import com.home._ac_front.course.domain.Course;
import com.home._ac_front.course.repository.CourseRepository;
import com.home._ac_front.user.User;
import com.home._ac_front.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository; // UserRepository 사용

    @Autowired
    private CourseRepository courseRepository; // CourseRepository 사용


    @Override
    public void run(String... args) throws Exception {



        // 이미 데이터가 있을 경우 삽입하지 않도록 조건 처리 (예: 초기 데이터 한 번만 삽입)
        if (userRepository.count() == 0) {
            // 사용자 정보 자동 삽입
            User user1 = User.builder()
                    .name("홍길동")
                    .studentId("20230001")
                    .department("컴퓨터소프트웨어학")
                    .course("학사")
                    .build();

            User user2 = User.builder()
                    .name("이영희")
                    .studentId("20230002")
                    .department("전자공학과")
                    .course("석사")
                    .build();

            // 데이터 삽입
            userRepository.save(user1);
            userRepository.save(user2);

            System.out.println("자동으로 데이터가 삽입되었습니다.");
        }
        // 강의 데이터 삽입
        if (courseRepository.count() == 0) {
            Course course1 = new Course(
                    "문정호", // 교수 이름
                    "컴퓨터 프로그래밍", // 강의 이름
                    "101호", // 강의실
                    "전공필수", // 구분
                    "월", // 강의 요일
                    "1학년", // 학년
                    1, // 시작 교시
                    3, // 종료 교시
                    3, // 학점
                    10, // 출석 점수
                    20, // 과제 점수
                    30, // 중간고사
                    40  // 기말고사
            );

            Course course2 = new Course(
                    "이석",
                    "전자회로",
                    "202호",
                    "전공선택",
                    "화",
                    "2학년",
                    2,
                    5,
                    3,
                    15,
                    25,
                    35,
                    45
            );

            Course course3 = new Course(
                    "문호수", // 교수 이름
                    "자바 프로그래밍", // 강의 이름
                    "101호", // 강의실
                    "전공필수", // 구분
                    "화", // 강의 요일
                    "1학년", // 학년
                    4, // 시작 교시
                    6, // 종료 교시
                    3, // 학점
                    10, // 출석 점수
                    20, // 과제 점수
                    30, // 중간고사
                    40  // 기말고사
            );


            courseRepository.save(course1);
            courseRepository.save(course2);
            courseRepository.save(course3);

            System.out.println("강의 데이터가 삽입되었습니다.");
        }
    }
}
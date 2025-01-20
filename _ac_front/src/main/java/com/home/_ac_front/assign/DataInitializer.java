package com.home._ac_front.assign;


import com.home._ac_front.user.User;
import com.home._ac_front.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository; // UserRepository 사용

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
    }
}
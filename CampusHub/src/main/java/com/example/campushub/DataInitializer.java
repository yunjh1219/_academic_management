package com.example.campushub;


import com.example.campushub.dept.domain.Dept;
import com.example.campushub.dept.repository.DeptRepository;
import com.example.campushub.schoolyear.domain.SchoolYear;
import com.example.campushub.schoolyear.domain.Semester;
import com.example.campushub.schoolyear.repository.SchoolYearRepository;
import com.example.campushub.user.domain.Role;
import com.example.campushub.user.domain.Status;
import com.example.campushub.user.domain.Type;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DeptRepository deptRepository;
    private final SchoolYearRepository schoolYearRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(DeptRepository deptRepository,
                           SchoolYearRepository schoolYearRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.deptRepository = deptRepository;
        this.schoolYearRepository = schoolYearRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // 데이터가 없을 때만 초기화
        if (deptRepository.count() == 0) {
            // Dept 엔티티에 데이터 삽입
            Dept dept1 = Dept.builder().deptName("소프트웨어학과").build();
            Dept dept2 = Dept.builder().deptName("컴퓨터공학과").build();

            // DB에 저장
            deptRepository.save(dept1);
            deptRepository.save(dept2);
        }

        if (schoolYearRepository.count() == 0) {
            // SchoolYear 엔티티에 데이터 삽입
            SchoolYear schoolYear1 = SchoolYear.builder()
                    .year(LocalDate.of(2025, 1, 1))  // LocalDate로 전체 날짜를 저장하지만 년도만 사용
                    .semester(Semester.first_semester)
                    .is_current(true)
                    .build();

            SchoolYear schoolYear2 = SchoolYear.builder()
                    .year(LocalDate.of(2025, 9, 1))
                    .semester(Semester.second_semester)
                    .is_current(false)
                    .build();

            SchoolYear schoolYear3 = SchoolYear.builder()
                    .year(LocalDate.of(2024, 9, 1))
                    .semester(Semester.first_semester)
                    .is_current(false)
                    .build();

            SchoolYear schoolYear4 = SchoolYear.builder()
                    .year(LocalDate.of(2024, 9, 1))
                    .semester(Semester.second_semester)
                    .is_current(false)
                    .build();

            // DB에 저장
            schoolYearRepository.save(schoolYear1);
            schoolYearRepository.save(schoolYear2);
            schoolYearRepository.save(schoolYear3);
            schoolYearRepository.save(schoolYear4);
        }

        // 관리자 계정 초기화
        if (userRepository.count() == 0) {
            User admin = User.builder()
                    .userNum("admin")
                    .password(passwordEncoder.encode("1234")) // 비밀번호 암호화
                    .userName("Admin User")
                    .birthday(LocalDateTime.of(1990, 1, 1, 0, 0)) // 생일 예시
                    .email("admin@example.com")
                    .phone("010-1234-5678")
                    .address("Admin Address")
                    .dept(null) // 관리자 계정은 학과가 없음
                    .grade(null) // 관리자 계정은 학년이 없음
                    .role(Role.ADMIN) // Role 설정
                    .type(Type.ADMIN) // Type 설정
                    .status(Status.EMPLOYED) // 상태 설정
                    .refreshToken(null) // 초기 리프레시 토큰
                    .build();

            userRepository.save(admin);
        }

        System.out.println("Departments, School Years, and Admin User initialized!");
    }
}

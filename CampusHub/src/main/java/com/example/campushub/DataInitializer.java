package com.example.campushub;


import com.example.campushub.dept.domain.Dept;
import com.example.campushub.dept.repository.DeptRepository;
import com.example.campushub.schoolyear.domain.SchoolYear;
import com.example.campushub.schoolyear.domain.Semester;
import com.example.campushub.schoolyear.repository.SchoolYearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DeptRepository deptRepository;
    private final SchoolYearRepository schoolYearRepository;

    @Autowired
    public DataInitializer(DeptRepository deptRepository, SchoolYearRepository schoolYearRepository) {
        this.deptRepository = deptRepository;
        this.schoolYearRepository = schoolYearRepository;
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

        System.out.println("Departments and School Years initialized!");
    }
}
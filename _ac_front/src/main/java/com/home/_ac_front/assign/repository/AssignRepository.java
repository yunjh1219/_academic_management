package com.home._ac_front.assign.repository;

import com.home._ac_front.assign.domain.Assign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignRepository extends JpaRepository<Assign, Long> {
    // 추가적인 쿼리 메서드를 필요에 따라 정의할 수 있습니다.

    // 강좌명과 주차로 과제 조회
    List<Assign> findByCourseNameAndWeek(String courseName, String week);

    // 강좌명으로 과제 조회
    List<Assign> findByCourseName(String courseName);

    // 주차로 과제 조회
    List<Assign> findByWeek(String week);

}
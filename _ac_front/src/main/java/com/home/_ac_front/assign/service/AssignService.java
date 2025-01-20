package com.home._ac_front.assign.service;

import com.home._ac_front.assign.domain.Assign;
import com.home._ac_front.assign.repository.AssignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignService {

    @Autowired
    private AssignRepository assignRepository;

    // 모든 과제 목록을 반환하는 메서드
    public List<Assign> getAllAssignments() {
        return assignRepository.findAll();  // 모든 과제 데이터를 반환
    }

    // 과제를 저장하는 메서드
    public Assign saveAssignment(Assign assign) {
        return assignRepository.save(assign);  // 저장 후 반환
    }


    // 강좌명 목록을 가져오는 메서드
    public List<String> getCourseNames() {
        return assignRepository.findAll()
                .stream()
                .map(Assign::getCourseName)  // 강좌명만 추출
                .distinct()  // 중복 제거
                .collect(Collectors.toList());
    }

    // 주차 목록을 가져오는 메서드
    public List<String> getWeeks() {
        return assignRepository.findAll()
                .stream()
                .map(Assign::getWeek)  // 주차만 추출
                .distinct()  // 중복 제거
                .collect(Collectors.toList());
    }

    // 강좌명과 주차로 과제 목록을 필터링하여 가져오는 메서드
    public List<Assign> getAssignments(String courseName, String week) {
        if (courseName != null && week != null) {
            return assignRepository.findByCourseNameAndWeek(courseName, week);  // 강좌명과 주차로 필터링
        } else if (courseName != null) {
            return assignRepository.findByCourseName(courseName);  // 강좌명으로만 필터링
        } else if (week != null) {
            return assignRepository.findByWeek(week);  // 주차로만 필터링
        } else {
            return assignRepository.findAll();  // 모든 과제 조회
        }
    }


}

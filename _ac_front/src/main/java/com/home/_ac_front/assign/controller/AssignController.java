package com.home._ac_front.assign.controller;

import com.home._ac_front.assign.domain.Assign;
import com.home._ac_front.assign.repository.AssignRepository;
import com.home._ac_front.assign.service.AssignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/assignments")  // 기본 URL 경로 설정
public class AssignController {

    @Autowired
    private AssignService assignService;

    // 과제 목록을 조회하는 API 엔드포인트
    @GetMapping
    public List<Assign> getAllAssignments() {
        return assignService.getAllAssignments();  // AssignService에서 과제 목록을 가져옴
    }

    // 과제를 저장하는 API 엔드포인트
    @PostMapping  // HTTP POST 요청 처리
    public Assign saveAssignment(@RequestBody Assign assign) {
        return assignService.saveAssignment(assign);  // AssignService에서 과제 저장
    }

    // 강좌명 목록을 가져오는 API 엔드포인트
    @GetMapping("/course-names")
    public List<String> getCourseNames() {
        return assignService.getCourseNames();  // 강좌명 목록 반환
    }

    // 주차 목록을 가져오는 API 엔드포인트
    @GetMapping("/weeks")
    public List<String> getWeeks() {
        return assignService.getWeeks();  // 주차 목록 반환
    }

    // 강좌명과 주차에 맞는 과제를 조회하는 API
    @GetMapping("/filter")  // 경로를 다르게 설정
    public List<Assign> getAssignments(
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String week) {
        return assignService.getAssignments(courseName, week);
    }
}

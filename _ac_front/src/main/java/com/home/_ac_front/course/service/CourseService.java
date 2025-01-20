package com.home._ac_front.course.service;

import com.home._ac_front.course.domain.Course;
import com.home._ac_front.course.dto.CourseTimeDTO;
import com.home._ac_front.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<CourseTimeDTO> getCourseSchedule() {
        List<Course> courses = courseRepository.findAll();  // 모든 강의 정보 가져오기

        // Course 객체에서 CourseTimeDTO로 변환
        return courses.stream()
                .map(course -> new CourseTimeDTO(
                        course.getCourseName(),  // 강의명
                        course.getCourseDay(),   // 요일
                        course.getStart(),       // 시작교시
                        course.getEnd()          // 끝나는교시
                ))
                .collect(Collectors.toList());  // 리스트로 반환
    }
}
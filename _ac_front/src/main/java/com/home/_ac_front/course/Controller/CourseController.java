package com.home._ac_front.course.Controller;

import com.home._ac_front.course.domain.Course;
import com.home._ac_front.course.dto.CourseTimeDTO;
import com.home._ac_front.course.repository.CourseRepository;
import com.home._ac_front.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    @RequestMapping("/api/course")
    public List<Course> showCourses(){
        return courseRepository.findAll();
    }

    // 수강신청된 강의를 저장하는 POST 메서드
    @PostMapping
    @RequestMapping("/api/course/registration")
    public Course saveCourseRegistration(@RequestBody Course course) {
        // 수강신청된 강의를 DB에 저장
        return courseRepository.save(course);
    }

    @RestController
    @RequestMapping("/api/course-schedule")
    public class CourseScheduleRestController {

        @Autowired
        private CourseService courseService;

        @GetMapping("/manage")
        public List<CourseTimeDTO> getCourseSchedule() {
            return courseService.getCourseSchedule();  // CourseTimeDTO 데이터를 반환
        }

    }


    @GetMapping
    @RequestMapping("/api/names")
    public List<String> getCourseNames() {
        return courseRepository.findAll()
                .stream()
                .map(Course::getCourseName)
                .collect(Collectors.toList());
    }



}

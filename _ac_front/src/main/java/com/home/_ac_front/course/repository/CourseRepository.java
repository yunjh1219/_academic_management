package com.home._ac_front.course.repository;


import com.home._ac_front.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository  extends JpaRepository<Course, Long> {
}

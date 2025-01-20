package com.example.campushub.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.campushub.course.domain.Course;

public interface CourseRepository extends JpaRepository<Course, Long> , CourseRepositoryCustom{
}

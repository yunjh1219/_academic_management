package com.example.campushub.course.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.campushub.course.domain.Course;
import com.example.campushub.user.domain.User;

public interface CourseRepository extends JpaRepository<Course, Long> , CourseRepositoryCustom {
	boolean existsByCourseName(String courseName);
	Optional<Course> findByCourseName(String courseName);
	Optional<Course> findByCourseNameAndUser(String courseName, User user);

}

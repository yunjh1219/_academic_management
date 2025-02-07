package com.example.campushub.exam.repository;

import com.example.campushub.exam.domain.Exam;
import com.example.campushub.usercourse.domain.UserCourse;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long>, ExamRepositoryCustom {
    List<Exam> findByUserCourseId(Long userCourseId);

	// List<Exam> findByUserCourse(UserCourse userCourse);
	Exam findByUserCourse(UserCourse userCourse);
}

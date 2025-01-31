package com.example.campushub.usercourse.repository;

import com.example.campushub.course.domain.Course;
import com.example.campushub.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.campushub.user.domain.User;
import com.example.campushub.usercourse.domain.UserCourse;

import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    List<UserCourse> findAllByCourse(Course course);

    UserCourse findByUser(User user);
    UserCourse findByCourseAndUser(Course course, User user);

    List<UserCourse> findAllByUser(User user);
	// 교수가 개설한 강의 목록 조회
	List<UserCourse> findAllByUser_UserNum(String userNum);

}

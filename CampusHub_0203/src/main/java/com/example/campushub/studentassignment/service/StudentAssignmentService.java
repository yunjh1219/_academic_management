package com.example.campushub.studentassignment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.campushub.assignment.domain.Assignment;
import com.example.campushub.assignment.repository.AssignmentRepository;
import com.example.campushub.course.domain.Course;
import com.example.campushub.global.error.exception.AlreadySubmittedException;
import com.example.campushub.global.error.exception.AssignmentNotFoundException;
import com.example.campushub.global.error.exception.StudentAssignmentNotFoundException;
import com.example.campushub.global.error.exception.UserCourseNotFoundException;
import com.example.campushub.global.error.exception.UserNotFoundException;
import com.example.campushub.studentassignment.domain.StudentAssignment;
import com.example.campushub.studentassignment.domain.SubmitStatus;
import com.example.campushub.studentassignment.dto.StudentAssigmentSearchCondition;
import com.example.campushub.studentassignment.dto.StudentAssignFindOneDto;
import com.example.campushub.studentassignment.dto.StudentAssignmentResponse;
import com.example.campushub.studentassignment.dto.StudentAssignmentSubmitDto;
import com.example.campushub.studentassignment.repository.StudentAssignmentRepository;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.repository.UserRepository;
import com.example.campushub.usercourse.domain.UserCourse;
import com.example.campushub.usercourse.repository.UserCourseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentAssignmentService {

	private final StudentAssignmentRepository studentAssignmentRepository;
	private final UserRepository userRepository;
	private final AssignmentRepository assignmentRepository;
	private final UserCourseRepository userCourseRepository;

	//학생 과제 작성
	@Transactional
	public void SubmitStudentAssignment(LoginUser loginUser, StudentAssignmentSubmitDto submitDto, Long assignmentId) {
		User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
			.orElseThrow(UserNotFoundException::new);

		Assignment assignment = assignmentRepository.findById(assignmentId)
			.orElseThrow(AssignmentNotFoundException::new);

		Course course = assignment.getNWeek().getCourse();

		UserCourse userCourse = userCourseRepository.findByUserAndCourse(user, course)
			.orElseThrow(UserCourseNotFoundException::new);

		StudentAssignment studentAssignment = studentAssignmentRepository.findByAssignmentAndUserCourse(assignment, userCourse)
			.orElseThrow(StudentAssignmentNotFoundException::new);

		if (studentAssignment.getStatus() == SubmitStatus.SUBMITTED) {
			throw new AlreadySubmittedException();
		}

		studentAssignment.submitAssignment(submitDto.getTitle(), submitDto.getContent());
	}

	//학생 과제 전체 조회
	public List<StudentAssignmentResponse> getAllStudentAssignment(LoginUser loginUser, StudentAssigmentSearchCondition cond) {
		User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
			.orElseThrow(UserNotFoundException::new);

		if (cond.getCourseName() == null || cond.getWeek() == null) {
			throw new IllegalArgumentException("강의명과 주차는 필수 입력값입니다.");
		}

		return studentAssignmentRepository.getAllStudentAssignments(cond);
	}
	//학생 과제 단건 조회
	public StudentAssignFindOneDto getStudentAssignment(LoginUser loginUser, Long assignmentId) {
		User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
			.orElseThrow(UserNotFoundException::new);

		return studentAssignmentRepository.getStudentAssignment(assignmentId)
			.orElseThrow(StudentAssignmentNotFoundException::new);
	}
	//학생 과제 점수 기입
	@Transactional
	public void setStudentAssignScore(LoginUser loginUser, Long studentAssignId, int score) {
		userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
			.orElseThrow(UserNotFoundException::new);

		 StudentAssignment studentAssignment = studentAssignmentRepository.findById(studentAssignId)
			 .orElseThrow(StudentAssignmentNotFoundException::new);

		 studentAssignment.editScore(score);
	}
}

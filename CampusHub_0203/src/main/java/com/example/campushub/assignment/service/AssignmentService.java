package com.example.campushub.assignment.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.campushub.assignment.domain.Assignment;
import com.example.campushub.assignment.dto.AssignmentCreateRequest;
import com.example.campushub.assignment.dto.AssignmentFindAllResponse;
import com.example.campushub.assignment.dto.AssignmentResponse;
import com.example.campushub.assignment.dto.AssignmentSearchCondition;
import com.example.campushub.assignment.repository.AssignmentRepository;
import com.example.campushub.course.domain.Course;
import com.example.campushub.course.repository.CourseRepository;
import com.example.campushub.global.error.exception.AssignmentNotFoundException;
import com.example.campushub.global.error.exception.CourseNotFoundException;
import com.example.campushub.global.error.exception.NWeekNotFoundException;
import com.example.campushub.global.error.exception.UserNotFoundException;
import com.example.campushub.nweek.domain.NWeek;
import com.example.campushub.nweek.domain.Week;
import com.example.campushub.nweek.repository.NweekRepository;
import com.example.campushub.studentassignment.domain.StudentAssignment;
import com.example.campushub.studentassignment.domain.SubmitStatus;
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
public class AssignmentService {

	private final AssignmentRepository assignmentRepository;
	private final NweekRepository nweekRepository;
	private final CourseRepository courseRepository;
	private final UserRepository userRepository;
	private final UserCourseRepository userCourseRepository;
	private final StudentAssignmentRepository studentAssignmentRepository;

	//과제 등록
	@Transactional
	public void createAssignment(LoginUser loginUser, AssignmentCreateRequest request) {
		User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
			.orElseThrow(UserNotFoundException::new);

		Course course = courseRepository.findByCourseNameAndUser(request.getCourseName(), user)
			.orElseThrow(CourseNotFoundException::new);

		NWeek nweek = nweekRepository.findByCourseAndWeek(course, Week.valueOf(request.getWeek()))
			.orElseThrow(NWeekNotFoundException::new);

		Assignment assignment = Assignment.builder()
			.nWeek(nweek)
			.assignExplain(request.getAssignExplain())
			.createDate(LocalDate.now())
			.limitDate(request.getLimitDate())
			.build();

		assignmentRepository.save(assignment);

		// 학생 과제 생성
		List<UserCourse> userCourses = userCourseRepository.findAllByCourse(course);

		for (UserCourse userCourse : userCourses) {
			StudentAssignment studentAssignment = StudentAssignment.builder()
				.assignment(assignment)
				.userCourse(userCourse)
				.status(SubmitStatus.NOT_SUBMITTED)
				.assignmentScore(0)
				.build();

			studentAssignmentRepository.save(studentAssignment);
		}
	}

	//과제 전체 조회(학생)
	public List<AssignmentFindAllResponse> findAllByCondition(LoginUser loginUser, AssignmentSearchCondition condition) {

		User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
			.orElseThrow(UserNotFoundException::new);

		List<UserCourse> userCourses = userCourseRepository.findAllByUser(user);

		List<String> courseNames = userCourses.stream()
			.map(userCourse -> userCourse.getCourse().getCourseName())
			.toList();
		return assignmentRepository.findAllAssigmentByCond(condition, courseNames);
	}
	//과제 단건 조회
	public AssignmentResponse findAssignment(LoginUser loginUser, Long assignmentId) {
		User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType()
		).orElseThrow(UserNotFoundException::new);

		return assignmentRepository.getAssignmentById(assignmentId)
			.orElseThrow(AssignmentNotFoundException::new);
	}


}

package com.example.campushub.course.service;

import java.util.List;

import com.example.campushub.course.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.campushub.course.domain.Course;
import com.example.campushub.course.repository.CourseRepository;
import com.example.campushub.global.error.exception.CourseNotFoundException;
import com.example.campushub.global.error.exception.DuplicateCourseException;
import com.example.campushub.global.error.exception.DuplicateRoomTimeException;
import com.example.campushub.global.error.exception.UserNotFoundException;
import com.example.campushub.nweek.domain.NWeek;
import com.example.campushub.nweek.domain.Week;
import com.example.campushub.nweek.repository.NweekRepository;
import com.example.campushub.schoolyear.domain.SchoolYear;
import com.example.campushub.schoolyear.dto.SchoolYearResponseDto;
import com.example.campushub.schoolyear.repository.SchoolYearRepository;
import com.example.campushub.user.domain.Type;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.repository.UserRepository;
import com.example.campushub.usercourse.domain.UserCourse;
import com.example.campushub.usercourse.repository.UserCourseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

	private final CourseRepository courseRepository;
	private final UserRepository userRepository;
	private final SchoolYearRepository schoolYearRepository;
	private final UserCourseRepository userCourseRepository;
	private final NweekRepository nWeekRepository;

	//교수 전체+컨디션 강의 조회
	public List<CourseResponseDto> findAllByProfCondition(LoginUser loginUser, ProfCourseSearchCondition cond) {
		userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
			.orElseThrow(UserNotFoundException::new);

		return courseRepository.findAllByProfCondition(cond);
	}

	//학생 전체 + 컨디션 강의 조회
	public List<CourseResponseDto> findAllByStudCondition(LoginUser loginUser, StudCourseSearchCondition cond) {
		userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
			.orElseThrow(UserNotFoundException::new);

		return courseRepository.findAllByStudCondition(cond);
	}

	//교수 본인 강의 조회
	public List<CourseResponseDto> findAllByProf(LoginUser loginUser) {
		User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
			.orElseThrow(UserNotFoundException::new);

		return courseRepository.findAllByProf(user.getUserNum());
	}
	//학생 본인 강의 조회
	public List<CourseResponseDto> findAllByStud(LoginUser loginUser) {
		User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
			.orElseThrow(UserNotFoundException::new);
		return courseRepository.findAllByStud(user.getUserNum());

	}

	//학생 수강 신청
	@Transactional
	public void joinCourse(LoginUser loginUser, List<Long> courseIds) {
		User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
			.orElseThrow(UserNotFoundException::new);

		for (Long courseId : courseIds) {
			Course course = courseRepository.findById(courseId)
				.orElseThrow(CourseNotFoundException::new);

			UserCourse userCourse = UserCourse.builder()
				.course(course)
				.user(user)
				.build();

			userCourseRepository.save(userCourse);
		}

	}

	//강의 생성
	@Transactional
	public void createCourse(LoginUser loginUser, CourseCreateDto createDto) {
		User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
			.orElseThrow(UserNotFoundException::new);

		//강의 중복 조건
		if (courseRepository.existsByCourseName(createDto.getCourseName())) {
			throw new DuplicateCourseException();
		}

		//강의실 중복 조건
		if (courseRepository.existsByRoomAndTime(createDto)) {
			throw new DuplicateRoomTimeException();
		}

		//학년도 학기 가져오기(학년도 엔티티중 iscurrent가 true인 엔티티 가져오기)
		SchoolYearResponseDto schoolYearDto = schoolYearRepository.getCurrentSchoolYear();

		SchoolYear schoolYear = SchoolYear.builder()
			.year(schoolYearDto.getYear())
			.semester(schoolYearDto.getSemester())
			.is_current(schoolYearDto.is_current())
			.build();

		Course course = createDto.toEntity(user, schoolYear);

		courseRepository.save(course);

		for (Week week : Week.values()) {

			NWeek nWeek = NWeek.builder()
				.course(course)
				.week(week)
				.build();

			nWeekRepository.save(nWeek);
		}
	}

	//강의 삭제
	@Transactional
	public void deleteCourse(LoginUser loginUser, Long courseId) {

		userRepository.findByUserNumAndType(loginUser.getUserNum(), Type.PROFESSOR)
			.orElseThrow(UserNotFoundException::new);

		courseRepository.findById(courseId)
				.orElseThrow(CourseNotFoundException::new);

		courseRepository.deleteById(courseId);
	}

	//강의 수정
	@Transactional
	public void editCourse(LoginUser loginUser, Long courseId, CourseEditDto editDto) {
		userRepository.findByUserNumAndType(loginUser.getUserNum(), Type.PROFESSOR)
			.orElseThrow(UserNotFoundException::new);

		Course course = courseRepository.findById(courseId)
			.orElseThrow(CourseNotFoundException::new);

		course.edit(editDto.getCourseName(), editDto.getRoom(), editDto.getDivision(), editDto.getCourseDay(), editDto.getCourseGrade(), editDto.getStartPeriod(),
			editDto.getEndPeriod(), editDto.getCredits(), editDto.getAttScore(), editDto.getAssignScore(), editDto.getMidScore(), editDto.getFinalScore());
	}

	//학생 본인 시간표 조회
	public List<CourseCalenderDto> findCalByStudent(LoginUser loginUser) {
		User student = userRepository.findByUserNumAndType(loginUser.getUserNum(),loginUser.getType())
				.orElseThrow(UserNotFoundException::new);

		return courseRepository.findCalByStud(student.getUserNum());
	}

	//교수 본인 시간표 조회
	public List<CourseCalenderDto> findCalByProf(LoginUser loginUser) {
		User professor = userRepository.findByUserNumAndType(loginUser.getUserNum(),loginUser.getType())
				.orElseThrow(UserNotFoundException::new);

		return courseRepository.findCalByProf(professor.getUserNum());
	}
}

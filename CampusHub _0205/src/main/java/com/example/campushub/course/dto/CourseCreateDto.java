package com.example.campushub.course.dto;

import com.example.campushub.course.domain.Course;
import com.example.campushub.course.domain.CourseDay;
import com.example.campushub.course.domain.CourseDivision;
import com.example.campushub.course.domain.CourseGrade;
import com.example.campushub.schoolyear.domain.SchoolYear;
import com.example.campushub.user.domain.User;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseCreateDto {
	@NotBlank(message = "강의명을 입력해주세요")
	private String courseName;
	@NotBlank(message = "강의실을 입력해주세요")
	private String room;
	@NotBlank(message = "이수구분을 선택해주세요")
	private String division;
	@NotBlank(message = "요일을 선택해주세요")
	private String courseDay;
	@NotBlank(message = "학년을 선택해주세요")
	private String courseGrade;
	@NotBlank(message = "시작 시간을 입력해주세요")
	private int startPeriod;
	@NotBlank(message = "종료시간을 입력해주세요")
	private int endPeriod;
	@NotBlank(message = "학점을 입력해주세요")
	private int credits;
	@NotBlank(message = "출석 기준 점수를 입력해주세요")
	private int attScore;
	@NotBlank(message = "과제 기준 점수를 입력해주세요")
	private int assignScore;
	@NotBlank(message = "중간 기준 점수를 입력해주세요")
	private int midScore;
	@NotBlank(message = "기말 기준 점수를 입력해주세요")
	private int finalScore;

	@Builder
	public CourseCreateDto(String courseName, String room, String division, String courseDay, String courseGrade, int startPeriod, int endPeriod,
		int credits, int attScore, int assignScore, int midScore, int finalScore) {
		this.courseName = courseName;
		this.room = room;
		this.division = division;
		this.courseDay = courseDay;
		this.courseGrade = courseGrade;
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
		this.credits = credits;
		this.attScore = attScore;
		this.assignScore = assignScore;
		this.midScore = midScore;
		this.finalScore = finalScore;
	}

	public Course toEntity(User user, SchoolYear schoolYear) {
		return Course.builder()
			.courseName(courseName)
			.room(room)
			.division(CourseDivision.of(division))
			.courseDay(CourseDay.of(courseDay))
			.courseGrade(CourseGrade.of(courseGrade))
			.user(user)
			.schoolYear(schoolYear)
			.startPeriod(startPeriod)
			.endPeriod(endPeriod)
			.creditScore(credits)
			.attScore(attScore)
			.assignScore(assignScore)
			.midExam(midScore)
			.finalExam(finalScore)
			.build();
	}

}

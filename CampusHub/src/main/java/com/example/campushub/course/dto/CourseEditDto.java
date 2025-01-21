package com.example.campushub.course.dto;

import com.example.campushub.course.domain.CourseDay;
import com.example.campushub.course.domain.CourseDivision;
import com.example.campushub.course.domain.CourseGrade;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseEditDto {

	@NotBlank(message = "강의명을 입력해주세요")
	private String courseName;
	@NotBlank(message = "강의실을 입력해주세요")
	private String room;
	@NotBlank(message = "이수구분을 선택해주세요")
	private CourseDivision division;
	@NotBlank(message = "요일을 선택해주세요")
	private CourseDay courseDay;
	@NotBlank(message = "학년을 선택해주세요")
	private CourseGrade courseGrade;
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
	public CourseEditDto(String courseName, String room, CourseDivision division, CourseDay courseDay,
		CourseGrade courseGrade, int startPeriod, int endPeriod, int credits, int attScore, int assignScore,
		int midScore,
		int finalScore) {
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
}

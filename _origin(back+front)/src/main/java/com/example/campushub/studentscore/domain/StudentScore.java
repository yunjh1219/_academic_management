package com.example.campushub.studentscore.domain;

import com.example.campushub.usercourse.domain.UserCourse;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentScore {

	@Id
	@Column(name = "student_score_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_course_id")
	private UserCourse userCourse;

	//총점
	private int totalScore;
	//등급
	private String grade;
	//학점
	private int credit;
	//백분율
	private int percentage;

	@Builder
	public StudentScore(UserCourse userCourse, int totalScore, String grade, int credit) {
		this.userCourse = userCourse;
		this.totalScore = totalScore;
		this.grade = grade;
		this.credit = credit;
		this.percentage = credit / totalScore;
	}
}

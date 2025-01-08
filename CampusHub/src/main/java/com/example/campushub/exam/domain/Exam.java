package com.example.campushub.exam.domain;

import static jakarta.persistence.FetchType.*;

import com.example.campushub.usercourse.domain.UserCourse;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exam {

	@Id
	@Column(name = "exam_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_course_id")
	private UserCourse userCourse;

	private int midScore;
	private int finalScore;
	private int totalScore;

	@Builder
	public Exam(UserCourse userCourse, int midScore, int finalScore, int totalScore) {
		this.userCourse = userCourse;
		this.midScore = midScore;
		this.finalScore = finalScore;
		this.totalScore = totalScore;
	}

}

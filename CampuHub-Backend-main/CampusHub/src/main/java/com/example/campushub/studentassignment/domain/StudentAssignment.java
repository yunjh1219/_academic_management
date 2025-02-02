package com.example.campushub.studentassignment.domain;

import java.time.LocalDate;

import com.example.campushub.assignment.domain.Assignment;
import com.example.campushub.usercourse.domain.UserCourse;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentAssignment {

	@Id
	@Column(name = "student_assignment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assignment_id")
	private Assignment assignment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_course_id")
	private UserCourse userCourse;

	private String assignmentTitle;
	private String assignmentContent;
	private LocalDate submitDate;
	private SubmitStatus status;
	private int assignmentScore;
	//첨부파일

	@Builder
	public StudentAssignment(Assignment assignment, UserCourse userCourse, String assignmentTitle, String assignmentContent,
		LocalDate submitDate, SubmitStatus status, int assignmentScore) {
		this.assignment = assignment;
		this.userCourse = userCourse;
		this.assignmentTitle = assignmentTitle;
		this.assignmentContent = assignmentContent;
		this.submitDate = submitDate;
		this.status = status;
		this.assignmentScore = assignmentScore;
	}

	public void submitAssignment(String title, String content) {
		this.status = SubmitStatus.SUBMITTED;
		this.assignmentTitle = title;
		this.assignmentContent = content;
		this.submitDate = LocalDate.now();
	}

	public void editScore(int score) {
		this.assignmentScore = score;
	}

}

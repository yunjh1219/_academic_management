package com.example.campushub.assignment.dto;

import java.time.LocalDate;

import com.example.campushub.nweek.domain.Week;
import com.example.campushub.studentassignment.domain.SubmitStatus;
import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssignmentFindAllResponse {

	private Long id;
	private String week;
	private String courseName;
	private String userName;
	private String submitStatus;
	private LocalDate limitDate;
	private LocalDate createDate;

	@Builder
	@QueryProjection
	public AssignmentFindAllResponse(Long id, Week week, String courseName, String userName, SubmitStatus submitStatus, LocalDate limitDate,
									 LocalDate createDate) {
		this.id = id;
		this.week = week.getName();
		this.courseName = courseName;
		this.userName = userName;
		this.submitStatus = submitStatus.getMessage();
		this.limitDate = limitDate;
		this.createDate = createDate;
	}
}

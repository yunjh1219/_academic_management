package com.example.campushub.assignment.dto;

import java.time.LocalDate;

import com.example.campushub.nweek.domain.Week;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssignmentFindAllResponse {

	private Long id;
	private String week;
	private String courseName;
	private String userName;
	private LocalDate limitDate;
	private LocalDate createDate;

	@Builder
	@QueryProjection
	public AssignmentFindAllResponse(Long id, Week week, String courseName, String userName, LocalDate limitDate,
		LocalDate createDate) {
		this.id = id;
		this.week = week.getName();
		this.courseName = courseName;
		this.userName = userName;
		this.limitDate = limitDate;
		this.createDate = createDate;
	}
}

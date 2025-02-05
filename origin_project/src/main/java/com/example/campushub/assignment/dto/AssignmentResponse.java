package com.example.campushub.assignment.dto;

import java.time.LocalDate;

import com.example.campushub.nweek.domain.Week;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssignmentResponse {
	private Long id;
	private String courseName;
	private String professorName;
	private String week;
	private LocalDate createDate;
	private String assignExplain;
	private LocalDate limitDate;

	@Builder
	@QueryProjection
	public AssignmentResponse(Long id, String courseName, String professorName, Week week, LocalDate createDate, String assignExplain,
		LocalDate limitDate) {
		this.id = id;
		this.courseName = courseName;
		this.professorName = professorName;
		this.week = week.getName();
		this.createDate = createDate;
		this.assignExplain = assignExplain;
		this.limitDate = limitDate;
	}
}

package com.example.campushub.assignment.dto;

import com.example.campushub.nweek.domain.Week;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssignmentSearchCondition {

	private String courseName;
	private String week;

	@Builder
	public AssignmentSearchCondition(String courseName, String week) {
		this.courseName = courseName;
		this.week = week;
	}
}

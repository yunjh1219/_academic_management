package com.example.campushub.studentassignment.dto;

import com.example.campushub.nweek.domain.Week;
import com.example.campushub.studentassignment.domain.SubmitStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentAssigmentSearchCondition {

	private String courseName;
	private String week;
	private String status;

	@Builder
	public StudentAssigmentSearchCondition(String courseName, String week, String status) {
		this.courseName = courseName;
		this.week = week;
		this.status = status;
	}
}

package com.example.campushub.exam.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExamSearchCondition {
	private String courseName;

	@Builder
	public ExamSearchCondition(String courseName) {
		this.courseName = courseName;
	}
}

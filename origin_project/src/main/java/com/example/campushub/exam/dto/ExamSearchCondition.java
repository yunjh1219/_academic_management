package com.example.campushub.exam.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExamSearchCondition {
	private String courseName;

	@Builder
	public ExamSearchCondition(String courseName) {
		this.courseName = courseName;
	}
}

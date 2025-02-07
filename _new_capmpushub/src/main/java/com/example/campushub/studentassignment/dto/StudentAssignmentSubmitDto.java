package com.example.campushub.studentassignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentAssignmentSubmitDto {
	private String title;
	private String content;

	@Builder
	public StudentAssignmentSubmitDto(String title, String content) {
		this.title = title;
		this.content = content;
	}
}

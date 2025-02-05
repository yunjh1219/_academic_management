package com.example.campushub.assignment.dto;

import java.time.LocalDate;

import com.example.campushub.nweek.domain.Week;
import com.querydsl.core.annotations.QueryProjection;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssignmentCreateRequest {

	@NotBlank(message = "강의명을 선택해주세요")
	private String courseName;
	@NotBlank(message = "주차를 선택해주세요")
	private String week;
	@NotBlank(message = "과제 설명란을 입력해주세요")
	private String assignExplain;
	@NotBlank(message = "제출 기한을 입력해주세요")
	private LocalDate limitDate;

	@Builder
	public AssignmentCreateRequest(String courseName, String week, String assignExplain, LocalDate limitDate) {
		this.courseName = courseName;
		this.week = week;
		this.assignExplain = assignExplain;
		this.limitDate = limitDate;
	}
}


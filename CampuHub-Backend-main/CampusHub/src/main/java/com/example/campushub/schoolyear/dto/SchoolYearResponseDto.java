package com.example.campushub.schoolyear.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.campushub.schoolyear.domain.Semester;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SchoolYearResponseDto {

	@DateTimeFormat(pattern = "yyyy")
	private LocalDate year;
	private Semester semester;
	private boolean is_current;

	@Builder
	@QueryProjection
	public SchoolYearResponseDto(LocalDate year, Semester semester, boolean is_current) {
		this.year = year;
		this.semester = semester;
		this.is_current = is_current;
	}
}

package com.example.campushub.schoolyear.dto;

import java.time.LocalDate;
import java.time.Year;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.campushub.schoolyear.domain.Semester;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SchoolYearResponseDto {

	private String year;
	private String semester;
	private boolean is_current;

	@Builder
	@QueryProjection
	public SchoolYearResponseDto(Year year, Semester semester, boolean is_current) {
		this.year = year.toString();
		this.semester = semester.getName();
		this.is_current = is_current;
	}
}

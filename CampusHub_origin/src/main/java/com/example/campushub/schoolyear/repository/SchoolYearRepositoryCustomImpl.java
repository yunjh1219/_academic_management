package com.example.campushub.schoolyear.repository;

import static com.example.campushub.schoolyear.domain.QSchoolYear.*;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.campushub.schoolyear.domain.SchoolYear;
import com.example.campushub.schoolyear.domain.Semester;
import com.example.campushub.schoolyear.dto.QSchoolYearResponseDto;
import com.example.campushub.schoolyear.dto.SchoolYearResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchoolYearRepositoryCustomImpl implements SchoolYearRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	//is_current 가 true고 원하는 semester 학년도 조회
	@Override
	public SchoolYearResponseDto getCurrentSchoolYear() {
		SchoolYearResponseDto fetchOne = queryFactory.select(new QSchoolYearResponseDto(
			schoolYear.year,
			schoolYear.semester,
			schoolYear.is_current
		))
			.from(schoolYear)
			.where(schoolYear.is_current.eq(true))
			.fetchOne();
		return fetchOne;
	}

}

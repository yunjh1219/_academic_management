package com.example.campushub.course.repository;

import java.util.List;

import com.example.campushub.course.dto.*;

public interface CourseRepositoryCustom {

	boolean existsByRoomAndTime(CourseCreateDto createDto);
	boolean existsByDayAndTime(CourseCreateDto createDto, String userNum);

	List<CourseResponseDto> findAllByProfCondition(ProfCourseSearchCondition condition);
	List<CourseResponseDto> findAllByStudCondition(StudCourseSearchCondition cond);
	List<CourseResponseDto>findAllByProf(String profNum);
	List<CourseResponseDto>findAllByStud(String studNum);

	List<CourseCalenderDto>findCalByStud(String userNum);
	List<CourseCalenderDto>findCalByProf(String userNum);

}

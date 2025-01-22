package com.example.campushub.course.repository;

import java.util.List;

import com.example.campushub.course.dto.CourseCreateDto;
import com.example.campushub.course.dto.CourseResponseDto;
import com.example.campushub.course.dto.ProfCourseSearchCondition;
import com.example.campushub.course.dto.StudCourseSearchCondition;

public interface CourseRepositoryCustom {

	boolean existsByRoomAndTime(CourseCreateDto createDto);

	List<CourseResponseDto> findAllByProfCondition(ProfCourseSearchCondition condition);
	List<CourseResponseDto> findAllByStudCondition(StudCourseSearchCondition cond);
	List<CourseResponseDto>findAllByProf(String profNum);
	List<CourseResponseDto>findAllByStud(String studNum);

}

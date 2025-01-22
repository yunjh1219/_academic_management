package com.example.campushub.course.dto;

import com.example.campushub.course.domain.CourseDay;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfCourseSearchCondition {
	private CourseDay courseDay;
	private String room;

	@Builder
	public ProfCourseSearchCondition(CourseDay courseDay, String room) {
		this.courseDay = courseDay;
		this.room = room;
	}
}

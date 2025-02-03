package com.example.campushub.course.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseDay {
	Monday("월요일"), TuesDay("화요일"), WednesDay("수요일"), ThursDay("목요일"), Friday("금요일");

	private final String name;
}

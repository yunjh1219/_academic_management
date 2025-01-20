package com.example.campushub.lecturetime.domain;

import static jakarta.persistence.FetchType.*;

import java.sql.Time;
import java.util.Date;

import com.example.campushub.course.domain.Course;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureTime {

	@Id
	@Column(name = "lecture_time_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "course_id")
	private Course course;

	private Date lectureDay;
	private Time lectureTime;

	@Builder
	public LectureTime(Course course, Date lectureDay, Time lectureTime) {
		this.course = course;
		this.lectureDay = lectureDay;
		this.lectureTime = lectureTime;
	}
}

package com.example.campushub.nweek.domain;

import static jakarta.persistence.FetchType.*;

import com.example.campushub.course.domain.Course;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NWeek {
	@Id
	@Column(name = "nweek_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "course_id")
	private Course course;

	@Enumerated(EnumType.STRING)
	private Week week;

	@Builder
	public NWeek(Course course, Week week) {
		this.course = course;
		this.week = week;
	}
}

package com.example.campushub.attendance.domain;

import static jakarta.persistence.FetchType.*;

import com.example.campushub.nweek.domain.NWeek;
import com.example.campushub.usercourse.domain.UserCourse;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance {

	@Id
	@Column(name = "attendance_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_course_id")
	private UserCourse userCourse;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "nweek_id")
	private NWeek nWeek;

	@Enumerated(EnumType.STRING)
	private AttendanceStatus status;

	@Builder
	public Attendance(UserCourse userCourse, NWeek nWeek, AttendanceStatus status) {
		this.userCourse = userCourse;
		this.nWeek = nWeek;
		this.status = status;
	}
}

package com.example.campushub.attendance.domain;

import static jakarta.persistence.FetchType.*;

import com.example.campushub.nweek.domain.NWeek;
import com.example.campushub.usercourse.domain.UserCourse;

import jakarta.annotation.Nullable;
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

	@Nullable
	@Enumerated(EnumType.STRING)
	private AttendanceStatus status;

	@Builder
	public Attendance(UserCourse userCourse, NWeek nWeek, AttendanceStatus status) {
		this.userCourse = userCourse;
		this.nWeek = nWeek;
		this.status = status;
	}

	public boolean isApplyAttendance() {
		if(this.status == AttendanceStatus.ATTENDANCE || this.status == AttendanceStatus.EARLY_DISMISSAL
				|| this.status == AttendanceStatus.ABSENCE || this.status == AttendanceStatus.PERCEPTION)
			return true;
		return false;
	}

	public void updateAttendance(AttendanceStatus newstatus) {
		if (this.status != newstatus) {
			this.status = newstatus;
		}
	}

	public void countAbsence() {
		int cnt = 0;
		if(this.status == AttendanceStatus.ABSENCE){
			cnt++;
			return ;
		}
	}
	public void edit(AttendanceStatus status) {
		this.status = status;
	}
}

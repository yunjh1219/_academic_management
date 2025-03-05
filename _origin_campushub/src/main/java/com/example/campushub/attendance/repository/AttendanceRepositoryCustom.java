package com.example.campushub.attendance.repository;



import com.example.campushub.attendance.domain.Attendance;
import com.example.campushub.attendance.dto.*;
import com.example.campushub.nweek.domain.NWeek;
import com.example.campushub.usercourse.domain.UserCourse;


import java.util.List;
import java.util.Optional;

public interface AttendanceRepositoryCustom {

    List<AttendanceResponseDto> findAllByCondition(AttendanceSearchCondition atten);

    List<AttendanceSummaryDto> findAttendanceByCondition(AttendanceSearchCourseCondition cond);

    List<AttendanceUserDto> findUserAttendance(String courseName, String userNum);
    Optional<Attendance> findByNWeekAndUserCourse(NWeek nWeek, UserCourse userCourse);
}

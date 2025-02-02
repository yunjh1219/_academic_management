package com.example.campushub.attendance.repository;



import com.example.campushub.attendance.dto.*;


import java.util.List;

public interface AttendanceRepositoryCustom {

    List<AttendanceResponseDto> findAllByCondition(AttendanceSearchCondition atten);

    // List<AllAttendanceResponseDto> findCourseByCondition(AttendanceSearchCourseCondition cond);
    List<AttendanceSummaryDto> findCourseByCondition(AttendanceSearchCourseCondition cond);

    List<AttendanceUserDto> findUserAttendance(AttendanceSearchCondition atten, String userNum);
}

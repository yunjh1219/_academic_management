package com.example.campushub.attendance.repository;

import com.example.campushub.attendance.domain.Attendance;
import com.example.campushub.attendance.domain.AttendanceStatus;
import com.example.campushub.nweek.domain.Week;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> , AttendanceRepositoryCustom {







}

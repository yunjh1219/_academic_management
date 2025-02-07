package com.example.campushub.semsterschedule.repository;

import com.example.campushub.semsterschedule.dto.SemesterScheduleResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface SemesterScheduleRepositoryCustom {

    List<SemesterScheduleResponse> findSchedulesByMonth(int year, int month);

    List<SemesterScheduleResponse> findSchedulesByDate(LocalDateTime date);
}

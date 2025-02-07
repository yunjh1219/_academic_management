package com.example.campushub.semsterschedule.repository;


import com.example.campushub.semsterschedule.domain.SemesterSchedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SemesterScheduleRepository extends JpaRepository<SemesterSchedule, Long>, SemesterScheduleRepositoryCustom {



}
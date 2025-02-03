package com.example.campushub.schoolyear.repository;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import com.example.campushub.schoolyear.domain.SchoolYear;
import com.example.campushub.schoolyear.domain.Semester;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolYearRepository extends JpaRepository<SchoolYear, Integer>, SchoolYearRepositoryCustom {

	Optional<SchoolYear> findBySemesterAndYear(Semester semester, Year year);
}

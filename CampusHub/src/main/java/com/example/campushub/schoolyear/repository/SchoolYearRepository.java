package com.example.campushub.schoolyear.repository;

import com.example.campushub.schoolyear.domain.SchoolYear;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolYearRepository extends JpaRepository<SchoolYear, Integer>, SchoolYearRepositoryCustom {
}

package com.example.campushub.nweek.repository;

import java.util.Optional;

import com.example.campushub.course.domain.Course;
import com.example.campushub.nweek.domain.NWeek;
import com.example.campushub.nweek.domain.Week;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NweekRepository extends JpaRepository<NWeek,Long> {
    NWeek findByWeek(Week week);

	Optional<NWeek> findByCourseAndWeek(Course course, Week week);
}

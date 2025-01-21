package com.example.campushub.nweek.repository;

import com.example.campushub.nweek.domain.NWeek;
import com.example.campushub.nweek.domain.Week;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NweekRepository extends JpaRepository<NWeek,Long> {
    NWeek findByWeek(Week week);
}

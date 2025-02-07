package com.example.campushub.tuition.repository;

import com.example.campushub.tuition.domain.Tuition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TuitionRepository extends JpaRepository<Tuition, Long> ,TuitionRepositoryCustom{
}

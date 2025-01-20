package com.example.campushub.schoolyear.domain;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolYear {
    @Id
    @Column(name = "school_year_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_year_year")
    private LocalDate year;
    @Enumerated(EnumType.STRING)
    private Semester semester;
    private boolean is_current;

    @Builder
    public SchoolYear(LocalDate year, Semester semester, boolean is_current) {
        this.year = year;
        this.semester = semester;
        this.is_current = is_current;
    }
}

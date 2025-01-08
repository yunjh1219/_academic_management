package com.example.campushub.schoolyear.domain;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AccessLevel;
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

    private Date year;
    @Enumerated(EnumType.STRING)
    private Semester semester;
    private boolean is_current;

}

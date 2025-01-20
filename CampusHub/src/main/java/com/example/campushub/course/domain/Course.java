package com.example.campushub.course.domain;

import com.example.campushub.schoolyear.domain.SchoolYear;
import com.example.campushub.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {

    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;
    private String room;
    private String division;
    @Column(name = "course_day")
    private CourseDay courseDay;

    //교수와 강의 매핑(비식별 관계)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //학년도 매핑
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "school_year")
    private SchoolYear schoolYear;

    @Column(name = "course_start")
    private int start;
    @Column(name = "course_end")
    private int end;
    private int credits;
    private int attScore;
    private int assignScore;
    private int midExam;
    private int finalExam;

    @Builder
    public Course(String courseName, String room, String division, CourseDay courseDay, User user, SchoolYear schoolYear, int start, int end, int credits,int attScore, int assignScore, int midExam, int finalExam){
        this.courseName = courseName;
        this.room = room;
        this.division = division;
        this.courseDay = courseDay;
        this.user = user;
        this.schoolYear = schoolYear;
        this.start = start;
        this.end = end;
        this.credits = credits;
        this.attScore = attScore;
        this.assignScore = assignScore;
        this.midExam = midExam;
        this.finalExam = finalExam;

    }


}

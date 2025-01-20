package com.home._ac_front.course.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Course {

    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String professorName;  // 교수 이름 추가
    private String courseName;
    private String room;
    private String division; // 전공필수 , 교양필수

    private String courseDay;

    @JoinColumn(name = "school_year")
    private String schoolYear;

    @Column(name = "course_start")
    private int start;  //시작 교시 1교시
    @Column(name = "course_end")
    private int end;  //끝나는 교시 4교시

    private int credits;
    private int attScore;
    private int assignScore;
    private int midExam;
    private int finalExam;

    @Builder
    public Course(String courseName, String room, String division, String courseDay, String schoolYear, String professorName, int start, int end, int credits,int attScore, int assignScore, int midExam, int finalExam){
        this.courseName = courseName;
        this.room = room;
        this.division = division;
        this.courseDay = courseDay;
        this.schoolYear = schoolYear;
        this.professorName = professorName;
        this.start = start;
        this.end = end;
        this.credits = credits;
        this.attScore = attScore;
        this.assignScore = assignScore;
        this.midExam = midExam;
        this.finalExam = finalExam;

    }

}

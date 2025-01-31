package com.example.campushub.course.domain;

import com.example.campushub.schoolyear.domain.SchoolYear;
import com.example.campushub.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {

    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;
    private String room;
    private CourseDivision division;
    @Column(name = "course_day")
    private CourseDay courseDay;
    private CourseGrade courseGrade;

    //교수와 강의 매핑(비식별 관계)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //학년도 매핑
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "school_year")
    private SchoolYear schoolYear;
    @Column(name = "course_start")
    private int startPeriod;
    @Column(name = "course_end")
    private int endPeriod;
    //학점
    private int creditScore;
    //출석점수
    private int attScore;
    //과제 점수
    private int assignScore;
    private int midExam;
    private int finalExam;

    @Builder
    public Course(String courseName, String room, CourseDivision division, CourseDay courseDay,CourseGrade courseGrade, User user, SchoolYear schoolYear,
        int startPeriod, int endPeriod, int creditScore,int attScore, int assignScore, int midExam, int finalExam){
        this.courseName = courseName;
        this.room = room;
        this.division = division;
        this.courseDay = courseDay;
        this.courseGrade = courseGrade;
        this.user = user;
        this.schoolYear = schoolYear;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.creditScore = creditScore;
        this.attScore = attScore;
        this.assignScore = assignScore;
        this.midExam = midExam;
        this.finalExam = finalExam;
    }

    public void edit(String courseName, String room, CourseDivision division, CourseDay courseDay,CourseGrade courseGrade,
        int startPeriod, int endPeriod, int creditScore,int attScore, int assignScore, int midExam, int finalExam) {
        this.courseName = courseName;
        this.room = room;
        this.division = division;
        this.courseDay = courseDay;
        this.courseGrade = courseGrade;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.creditScore = creditScore;
        this.attScore = attScore;
        this.assignScore = assignScore;
        this.midExam = midExam;
        this.finalExam = finalExam;
    }


}

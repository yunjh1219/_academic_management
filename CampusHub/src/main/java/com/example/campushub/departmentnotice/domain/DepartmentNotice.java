package com.example.campushub.departmentnotice.domain;

import com.example.campushub.user.domain.User;
import com.example.campushub.usercourse.domain.UserCourse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepartmentNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_notice_id")
    private Long id;

    private String title; // 공지 제목
    private String content; // 공지 내용
    private Date createdAt; // 생성 날짜

    // 작성자와 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    // 특정 강의(UserCourse)와 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_course_id")
    private UserCourse userCourse;

    @Builder
    public DepartmentNotice(String title, String content, Date createdAt, User author, UserCourse userCourse) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.author = author;
        this.userCourse = userCourse;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

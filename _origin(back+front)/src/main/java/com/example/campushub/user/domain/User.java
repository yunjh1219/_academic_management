package com.example.campushub.user.domain;

import com.example.campushub.dept.domain.Dept;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String password;
    private String userName;
    private String birthday;

    //학과 매핑
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dept_id")
    private Dept dept;

    //사용자 강의 매핑
    // @OneToMany(mappedBy = "user")
    // private List<CourseEnrollment> courseEnrollments = new ArrayList<>();

    //교수와 강의 매핑
   // @OneToMany(mappedBy = "user")
   //  private List<Course> courses = new ArrayList<>();

    //학적변동이력 x?

    //등록 장학 정보 매핑

    //학기 성적 매핑


    private String email;
    private String phone;
    private String address;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public User(String password, String userName, String birthday, String email, String phone, String address, Grade grade, Role role, Type type, Status status, Dept dept) {
        this.password = password;
        this.userName = userName;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.grade = grade;
        this.role = role;
        this.type = type;
        this.status = status;
        this.dept = dept;
    }

}
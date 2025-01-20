package com.example.campushub.user.domain;

import com.example.campushub.dept.domain.Dept;
import com.example.campushub.global.error.exception.IsNotPendingStatusException;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String userNum;
    private String password;
    private String userName;
    private LocalDateTime birthday;

    //학과 매핑
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dept_id")
    private Dept dept;

    private String email;
    private String phone;
    private String address;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Enumerated(EnumType.STRING)
    @Nullable
    private Grade grade;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String refreshToken;

    @Builder
    public User(String userNum,String password, String userName, LocalDateTime birthday, String email, String phone, String address,
        Dept dept, Grade grade, Role role, Type type, Status status, String refreshToken) {
        this.userNum = userNum;
        this.password = password;
        this.userName = userName;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dept = dept;
        this.grade = grade;
        this.role = role;
        this.type = type;
        this.status = status;
        this.refreshToken = refreshToken;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void invalidateRefreshToken() {
        this.refreshToken = null;
    }

    public Boolean isPendingStatus() {
        if (this.status == Status.BREAK_PENDING || this.status == Status.RETURN_PENDING)
            return true;
        return false;
    }

    public Boolean isApplyStatus() {
        if(this.status == Status.ENROLLED || this.status == Status.BREAK)
            return true;
        return false;
    }

    public void updatePendingStatus() {
        if (this.status == Status.BREAK_PENDING) {
            this.status = Status.BREAK;
        }
        else if (this.status == Status.RETURN_PENDING) {
            this.status = Status.ENROLLED;
        }
    }
    public void updateBreakPendingStatus() {
         this.status = Status.BREAK;
    }


    public void updateStatus() {
        if(this.status == Status.ENROLLED)
            this.status = Status.BREAK_PENDING;
        if(this.status == Status.BREAK)
            this.status = Status.RETURN_PENDING;
    }

}
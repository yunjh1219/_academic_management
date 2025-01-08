package com.example.campushub.dept.domain;

import com.example.campushub.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private Long id;
    private String deptName;

    //사용자 매핑
    // @OneToMany(mappedBy = "dept")
    // private List<User> users = new ArrayList<>();

    //학기등록금 매핑
    @Builder
    public Dept(String deptName) {
        this.deptName = deptName;
    }


}

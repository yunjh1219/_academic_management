package com.example.campushub.dept.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
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

    public void edit(String deptName) {

        this.deptName = deptName;
    }

}

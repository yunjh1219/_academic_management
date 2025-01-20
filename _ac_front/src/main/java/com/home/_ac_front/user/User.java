package com.home._ac_front.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 과제 ID (자동 생성)

    private String name; // 사용자 이름
    private String studentId; // 학번
    private String department; // 학과
    private String course; // 과정 (예: 학부, 석사, 박사 등)
}

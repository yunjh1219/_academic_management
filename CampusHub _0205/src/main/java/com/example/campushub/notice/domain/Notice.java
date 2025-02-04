package com.example.campushub.notice.domain;


import com.example.campushub.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

    @Id
    @Column(name = "notice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;              //공지ID

    //관리자와 공지 매핑(비식별 관계)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private NoticeType noticetype;
    private String noticeTitle;   //공지 제목
    private String noticeContent; //공지 내용
    private LocalDateTime createdAt; //생성 날짜 년.월.일.시간
    private LocalDateTime updatedAt; //수정 날짜 년.월.일.시간

    @Builder
    public Notice(User user, NoticeType noticeType, String noticeTitle, String noticeContent, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.user = user;
        this.noticetype = noticeType;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}

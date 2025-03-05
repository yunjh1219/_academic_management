package com.example.campushub.notice.domain;

import com.example.campushub.user.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

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

    //유저(교수,관리자)와 매핑
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private NoticeType noticeType;
    private String noticeTitle;   //공지 제목
    private String noticeContent; //공지 내용
    @CreatedDate
    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime createdAt; //생성 날짜 년.월.일.시간
    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime updatedAt; //수정 날짜 년.월.일.시간

    @Builder
    public Notice(User user, NoticeType noticeType, String noticeTitle, String noticeContent, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.user = user;
        this.noticeType = noticeType;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}

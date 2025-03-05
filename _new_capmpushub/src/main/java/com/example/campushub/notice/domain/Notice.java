package com.example.campushub.notice.domain;

import com.example.campushub.notice.dto.NoticeEditDto;
import com.example.campushub.user.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString
@Table(name = "notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Notice {

    @Id
    @Column(name = "notice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public void edit(NoticeEditDto editDto) {

        this.noticeType = NoticeType.of(editDto.getNoticeType());
        this.noticeTitle = editDto.getNoticeTitle();
        this.noticeContent = editDto.getNoticeContent();

    }

}

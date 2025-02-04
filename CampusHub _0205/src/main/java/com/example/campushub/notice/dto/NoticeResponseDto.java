package com.example.campushub.notice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeResponseDto {
    private Long id; //아이디
    private String noticeTitle; //제목
    private String noticeContent; //내용
    private String createdAt; // 생성일
    private String updatedAt; // 수정일
    private String userName; // 유저 이름
}

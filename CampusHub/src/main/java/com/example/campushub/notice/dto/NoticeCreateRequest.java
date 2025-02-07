package com.example.campushub.notice.dto;

import com.example.campushub.notice.domain.Notice;
import com.example.campushub.notice.domain.NoticeType;
import com.example.campushub.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Getter
@RequiredArgsConstructor
public class NoticeCreateRequest {
    @NotBlank(message = "타입을 선택해주세요")
    private String noticeType;
    @NotBlank(message = "제목을 입력해주세요")
    private String noticeTitle;
    @NotBlank(message = "내용을 입력해주세요")
    private String noticeContent;

    public Notice toEntity(User user) {


        return  Notice.builder()
                .noticeTitle(noticeTitle)
                .noticeContent(noticeContent)
                .noticeType(NoticeType.of(noticeType))
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

    }

}



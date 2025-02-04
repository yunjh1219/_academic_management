package com.example.campushub.notice.dto;

import com.example.campushub.notice.domain.Notice;
import com.example.campushub.notice.domain.NoticeType;
import com.example.campushub.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Getter
@RequiredArgsConstructor
public class NoticeCreateDto {
    @NotBlank(message = "타입을 선택해주세요")
    private String noticeType;
    @NotBlank(message = "제목을 입력해주세요")
    private String noticeTitle;
    @NotBlank(message = "내용을 입력해주세요")
    private String noticeContent;

    public Notice toEntity(User user) {

        // 로그로 값 확인
        log.info("NoticeTy55555555555pe: {}", noticeType);  // '학사' 값이 출력되어야 함

        return  Notice.builder()
                .noticeTitle(noticeTitle)
                .noticeContent(noticeContent)
                .noticeType(NoticeType.of(noticeType))
                .user(user)
                .build();

    }

}

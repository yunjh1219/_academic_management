package com.home._front.controller.notice;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoticeDto {
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdDate;
}

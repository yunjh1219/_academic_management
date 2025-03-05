package com.example.campushub.deptNotice.dto;

import com.example.campushub.dept.domain.Dept;
import com.example.campushub.deptNotice.domain.DeptNotice;
import com.example.campushub.deptNotice.domain.DeptNoticeType;
import com.example.campushub.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeptNoticeCreateRequestDto {

    @NotBlank(message = "공지 타입을 선택해주세요")
    private String deptNoticeType;
    @NotBlank(message = "학과를 선택해주세요")
    private String deptName;
    @NotBlank(message = "제목을 입력해주세요")
    private String noticeTitle;
    @NotBlank(message = "내용을 입력해주세요")
    private String noticeContent;

    public DeptNotice toEntity(User user, Dept dept) {
        return DeptNotice.builder()
                .deptNoticeType(DeptNoticeType.of(deptNoticeType))
                .dept(dept)
                .noticeTitle(noticeTitle)
                .noticeContent(noticeContent)
                .user(user)
                .build();
    }


}

package com.example.campushub.notice.service;

import com.example.campushub.global.error.exception.NoticeNotFoundException;
import com.example.campushub.global.error.exception.UserNotFoundException;
import com.example.campushub.notice.domain.Notice;
import com.example.campushub.notice.dto.*;
import com.example.campushub.notice.repository.NoticeRepository;
import com.example.campushub.user.domain.Type;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    //공지사항 생성
    @Transactional
    public void createNotice(LoginUser loginUser, NoticeCreateRequestDto createDto) {
        User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
                .orElseThrow(UserNotFoundException::new);

        Notice notice = createDto.toEntity(user);

        noticeRepository.save(notice);
    }

    //공지사항 수정
    @Transactional
    public void editNotice(LoginUser loginUser, Long noticeId, NoticeEditDto editDto){
        userRepository.findByUserNumAndType(loginUser.getUserNum(), Type.ADMIN)
                .orElseThrow(UserNotFoundException::new);

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(NoticeNotFoundException::new);

        notice.edit(editDto);
    }

    //공지사항 삭제
    @Transactional
    public void deleteNoticeByNoticeId(LoginUser loginUser, Long noticeId) {

        userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
                .orElseThrow(UserNotFoundException::new);

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("삭제하려는 공지사항을 찾을 수 없습니다."));

        noticeRepository.delete(notice);
    }

    //공지사항 전체 + 컨디션 조회
    public List<NoticeFindAllResponseDto> findAllByNoticeCondition(LoginUser loginUser, NoticeSearchCondition cond) {
        if (loginUser != null) {
            userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
                    .orElseThrow(UserNotFoundException::new);
        }

        return noticeRepository.findAllByNoticeCondition(cond);
    }

    //공지사항 단건 조회
    public NoticeFindOneResponseDto findNotice(LoginUser loginUser, Long noticeId) {
        if (loginUser != null) {
            userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
                    .orElseThrow(UserNotFoundException::new);
        }

        return noticeRepository.getNoticeById(noticeId)
                .orElseThrow(NoticeNotFoundException::new);
    }

}

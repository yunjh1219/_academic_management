package com.example.campushub.notice.service;

import com.example.campushub.global.error.exception.UserNotFoundException;
import com.example.campushub.notice.domain.Notice;
import com.example.campushub.notice.dto.NoticeCreateDto;
import com.example.campushub.notice.dto.NoticeResponseDto;
import com.example.campushub.notice.dto.NoticeTypeSearchCondition;
import com.example.campushub.notice.repository.NoticeRepository;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    //공지생성
    @Transactional
    public void createNotice(LoginUser loginUser, NoticeCreateDto createDto) {
        User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
            .orElseThrow(UserNotFoundException::new);

        Notice notice = createDto.toEntity(user);

        noticeRepository.save(notice);
    }

    //타입에 따라 전체 공지 조회
    public List<NoticeResponseDto> findAllByTypeCondition(LoginUser loginUser, NoticeTypeSearchCondition cond){
        userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
                .orElseThrow(UserNotFoundException::new);

        return noticeRepository.findAllByTypeCondition(cond);
    }

}

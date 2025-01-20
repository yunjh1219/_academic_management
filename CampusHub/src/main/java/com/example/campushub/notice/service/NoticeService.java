package com.example.campushub.notice.service;

import com.example.campushub.global.error.exception.NoticeNotFoundException;
import com.example.campushub.global.error.exception.UserNotFoundException;
import com.example.campushub.notice.domain.Notice;
import com.example.campushub.notice.dto.NoticeCreateRequestDto;
import com.example.campushub.notice.dto.NoticeListAll;
import com.example.campushub.notice.dto.NoticeResponseDto;
import com.example.campushub.notice.dto.NoticeSearchCondition;
import com.example.campushub.notice.repository.NoticeRepository;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    // 공지사항 전체 조회
    public Page<NoticeListAll> getNoticesByCondition(NoticeSearchCondition condition, int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size); // 0-based index

        // 조건에 따른 검색 처리
        if ("title".equals(condition.getFilter())) {
            return noticeRepository.findAllByCondition(condition.getKeyword(), null, pageable);
        } else if ("createdBy".equals(condition.getFilter())) {
            return noticeRepository.findAllByCondition(null, condition.getKeyword(), pageable);
        } else {
            throw new IllegalArgumentException("잘못된 검색 필터: " + condition.getFilter());
        }
    }

    // 공지사항 상세 조회
    public NoticeResponseDto getNoticeDetail(Long id) {
        NoticeResponseDto notice = noticeRepository.findNoticeDetailById(id);
        if (notice == null) {
            throw new NoticeNotFoundException();
        }
        return notice;
    }

    // 공지사항 삭제
    @Transactional
    public void deleteNotice(Long id, String userType) {
        // 관리자 권한 확인
        if (!"ADMIN".equals(userType)) {
            throw new AccessDeniedException("공지사항 삭제 권한이 없습니다.");
        }

        // 공지사항 존재 여부 확인
        if (!noticeRepository.existsById(id)) {
            throw new NoticeNotFoundException();
        }

        // 공지사항 삭제
        noticeRepository.deleteById(id);
    }

    // 공지사항 작성
    @Transactional
    public void createNotice(NoticeCreateRequestDto requestDto, Long userId) {
        // 유저 ID를 기반으로 User 엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        // Notice 엔티티 생성
        Notice notice = Notice.builder()
                .user(user)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .createdAt(new Date())
                .build();

        // Notice 엔티티 저장
        noticeRepository.save(notice);
    }

    // 공지사항 수정
    @Transactional
    public void updateNotice(Long id, NoticeCreateRequestDto requestDto, Long userId) {
        // 공지사항 조회
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(NoticeNotFoundException::new);

        // 작성자 확인
        if (!notice.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }

        // 공지사항 수정
        notice.update(requestDto.getTitle(), requestDto.getContent());
    }
}

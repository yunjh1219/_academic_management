package com.example.campushub.notice.service;

import com.example.campushub.global.config.QueryDslConfig;
import com.example.campushub.global.error.exception.NoticeNotFoundException;
import com.example.campushub.global.error.exception.UserNotFoundException;
import com.example.campushub.notice.domain.Notice;
import com.example.campushub.notice.dto.NoticeCreateRequestDto;
import com.example.campushub.notice.dto.NoticeListAll;
import com.example.campushub.notice.dto.NoticeResponseDto;
import com.example.campushub.notice.dto.NoticeSearchCondition;
import com.example.campushub.notice.repository.NoticeRepository;
import com.example.campushub.user.domain.Role;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Slf4j
@DataJpaTest
@Import({QueryDslConfig.class, NoticeService.class})
class NoticeServiceTest {

    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoticeService noticeService;

    @AfterEach
    public void tearDown(){
        noticeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("공지사항 전체 조회 - 제목 필터를 사용하여 검색")
    void testGetNoticesByCondition_withTitleFilter() {
        // Given: 테스트 데이터 준비
        User admin = userRepository.save(createAdmin("200001", "관리자"));
        createNoticesForUser(admin, "공지사항", "내용");

        PageRequest pageable = PageRequest.of(1, 10); // 1페이지, 10개씩

        NoticeSearchCondition condition = NoticeSearchCondition.builder()
                .filter("title")
                .keyword("공지사항")
                .build();

        // When: 서비스 호출
        Page<NoticeListAll> result = noticeService.getNoticesByCondition(condition, pageable.getPageNumber(), pageable.getPageSize());

        // Then: 결과 검증
        assertThat(result.getTotalElements()).isEqualTo(20); // 전체 데이터 개수
        assertThat(result.getContent().size()).isEqualTo(10); // 현재 페이지 데이터 개수
        assertThat(result.getContent().get(0).getNoticeTitle()).contains("공지사항"); // 첫 번째 데이터 확인
    }

    @Test
    @DisplayName("공지사항 전체 조회 - 작성자 필터를 사용하여 검색")
    void testGetNoticesByCondition_withCreatedByFilter() {
        // Given: 테스트 데이터 준비
        User admin = userRepository.save(createAdmin("200001", "관리자"));
        createNoticesForUser(admin, "공지사항", "내용");

        PageRequest pageable = PageRequest.of(1, 10); // 1페이지, 10개씩

        NoticeSearchCondition condition = NoticeSearchCondition.builder()
                .filter("createdBy")
                .keyword("관리자")
                .build();

        // When: 서비스 호출
        Page<NoticeListAll> result = noticeService.getNoticesByCondition(condition, pageable.getPageNumber(), pageable.getPageSize());

        // Then: 결과 검증
        assertThat(result.getTotalElements()).isEqualTo(20); // 전체 데이터 개수
        assertThat(result.getContent().size()).isEqualTo(10); // 현재 페이지 데이터 개수
        assertThat(result.getContent().get(0).getUserName()).contains("관리자"); // 첫 번째 데이터 확인
    }

    @Test
    @DisplayName("공지사항 상세 조회 - 성공 시 데이터 반환")
    void testGetNoticeDetail_success() {
        // Given: 테스트 데이터 준비
        User admin = userRepository.save(createAdmin("200001", "관리자"));
        Notice savedNotice = noticeRepository.save(createNotice(admin, "공지사항 제목", "공지사항 내용"));

        // When: 서비스 호출
        NoticeResponseDto result = noticeService.getNoticeDetail(savedNotice.getId());

        // Then: 결과 검증
        assertThat(result.getTitle()).isEqualTo("공지사항 제목");
        assertThat(result.getContent()).isEqualTo("공지사항 내용");
        assertThat(result.getCreatedBy()).isEqualTo(admin.getUserName());
    }

    @Test
    @DisplayName("공지사항 상세 조회 - 공지사항을 찾을 수 없는 경우 예외 발생")
    void testGetNoticeDetail_notFound() {
        // Given: 데이터베이스에 저장되지 않은 공지사항 ID
        Long invalidNoticeId = 999L;

        // When & Then: 존재하지 않는 ID로 조회 시 예외 발생 검증
        assertThatThrownBy(() -> noticeService.getNoticeDetail(invalidNoticeId))
                .isInstanceOf(NoticeNotFoundException.class); // 예외 메시지 확인
    }

    @Test
    @DisplayName("공지사항 작성 - 성공적으로 저장")
    void testCreateNotice_success() {
        // Given: 테스트 데이터 준비
        User user = userRepository.save(createAdmin("200001", "관리자")); // 관리자 유저 생성
        NoticeCreateRequestDto requestDto = new NoticeCreateRequestDto("새 공지사항 제목", "새 공지사항 내용");

        // When: 공지사항 저장
        noticeService.createNotice(requestDto, user.getId());

        // Then: 저장된 데이터 검증
        List<Notice> notices = noticeRepository.findAll();
        assertThat(notices).hasSize(1); // 저장된 공지사항이 하나 있어야 함
        Notice savedNotice = notices.get(0);
        assertThat(savedNotice.getTitle()).isEqualTo("새 공지사항 제목");
        assertThat(savedNotice.getContent()).isEqualTo("새 공지사항 내용");
        assertThat(savedNotice.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("공지사항 작성 - 유저를 찾을 수 없는 경우 예외 발생")
    void testCreateNotice_userNotFound() {
        // Given: 존재하지 않는 유저 ID
        Long invalidUserId = 999L;
        NoticeCreateRequestDto requestDto = new NoticeCreateRequestDto("새 공지사항 제목", "새 공지사항 내용");

        // When & Then: 존재하지 않는 유저 ID로 예외 발생 검증
        assertThatThrownBy(() -> noticeService.createNotice(requestDto, invalidUserId))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("공지사항 수정 - 성공적으로 수정")
    void testUpdateNotice_success() {
        // Given: 테스트 데이터 준비
        User user = userRepository.save(createAdmin("200001", "관리자")); // 관리자 유저 생성
        Notice notice = noticeRepository.save(createNotice(user, "기존 제목", "기존 내용")); // 기존 공지사항 생성
        NoticeCreateRequestDto updateRequest = new NoticeCreateRequestDto("수정된 제목", "수정된 내용");

        // When: 공지사항 수정
        noticeService.updateNotice(notice.getId(), updateRequest, user.getId());

        // Then: 수정된 데이터 검증
        Notice updatedNotice = noticeRepository.findById(notice.getId())
                .orElseThrow(NoticeNotFoundException::new);
        assertThat(updatedNotice.getTitle()).isEqualTo("수정된 제목");
        assertThat(updatedNotice.getContent()).isEqualTo("수정된 내용");
    }

    @Test
    @DisplayName("공지사항 수정 - 존재하지 않는 공지사항 예외")
    void testUpdateNotice_notFound() {
        // Given: 존재하지 않는 공지사항 ID와 수정 요청 데이터
        User user = userRepository.save(createAdmin("200001", "관리자")); // 관리자 유저 생성
        Long invalidNoticeId = 999L;
        NoticeCreateRequestDto updateRequest = new NoticeCreateRequestDto("수정된 제목", "수정된 내용");

        // When & Then: 존재하지 않는 공지사항 ID로 예외 발생 검증
        assertThatThrownBy(() -> noticeService.updateNotice(invalidNoticeId, updateRequest, user.getId()))
                .isInstanceOf(NoticeNotFoundException.class);
    }


    private void createNoticesForUser(User user, String noticeTitlePrefix, String noticeContentPrefix) {
        for (int i = 1; i <= 20; i++) {
            Notice notice = createNotice(user, noticeTitlePrefix + " " + i, noticeContentPrefix + " " + i);
            noticeRepository.save(notice);
            log.info("공지사항 생성: {}", notice);
        }
    }

    private User createAdmin(String userNum, String userName) {
        return User.builder()
                .userName(userName)
                .userNum(userNum)
                .password("1234")
                .email("aaa@aaa.com")
                .role(Role.ADMIN)
                .build();
    }

    private User createUser(String userNum, String userName) {
        return User.builder()
                .userName(userName)
                .userNum(userNum)
                .password("1234")
                .email("bbb@bbb.com")
                .role(Role.USER)
                .build();
    }

    private Notice createNotice(User user, String noticeTitle, String noticeContent) {
        return Notice.builder()
                .user(user)
                .title(noticeTitle)
                .content(noticeContent)
                .build();
    }
}

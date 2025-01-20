package com.example.campushub.notice.controller;

import com.example.campushub.notice.domain.Notice;
import com.example.campushub.notice.dto.NoticeCreateRequestDto;
import com.example.campushub.notice.dto.NoticeListAll;
import com.example.campushub.notice.dto.NoticeResponseDto;
import com.example.campushub.notice.repository.NoticeRepository;
import com.example.campushub.notice.service.NoticeService;
import com.example.campushub.user.domain.Role;
import com.example.campushub.user.domain.Type;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class NoticeControllerTest {

    @Autowired
    private NoticeController noticeController;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        noticeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("공지사항 전체 조회 - 성공")
    void testGetNotices() {
        // Given
        User admin = userRepository.save(createAdmin("200001", "관리자"));
        createNoticesForUser(admin, "테스트 공지사항", "테스트 내용");

        int page = 1; // 1-based index
        int size = 5;

        // When
        ResponseEntity<?> response = noticeController.getNotices(page, size, "title", "테스트");

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue(); // 상태 코드 검증
        assertThat(response.getBody()).isInstanceOf(Page.class); // 반환 타입 검증

        @SuppressWarnings("unchecked")
        List<NoticeListAll> notices = ((Page<NoticeListAll>) response.getBody()).getContent();
        assertThat(notices).isNotNull();
        assertThat(notices.size()).isEqualTo(size); // 페이징 크기 확인
        assertThat(notices.get(0).getNoticeTitle()).contains("테스트 공지사항");
    }

    @Test
    @DisplayName("공지사항 상세 조회 - 성공")
    void testGetNoticeDetail() {
        // Given
        User admin = userRepository.save(createAdmin("200001", "관리자"));
        Notice notice = noticeRepository.save(createNotice(admin, "테스트 공지사항", "테스트 내용"));

        // When
        ResponseEntity<NoticeResponseDto> response = noticeController.getNoticeDetail(notice.getId());

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue(); // 상태 코드 검증
        NoticeResponseDto responseDto = response.getBody();
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getTitle()).isEqualTo("테스트 공지사항");
        assertThat(responseDto.getContent()).isEqualTo("테스트 내용");
    }

    @Test
    @DisplayName("공지사항 작성 - 성공")
    void testCreateNotice() {
        // Given
        User admin = userRepository.save(createAdmin("200001", "관리자"));
        NoticeCreateRequestDto requestDto = new NoticeCreateRequestDto("새 공지사항", "새 공지사항 내용");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", admin.getId());

        // When
        ResponseEntity<?> response = noticeController.createNotice(requestDto, session);

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue(); // 상태 코드 검증

        // Verify
        Notice savedNotice = noticeRepository.findAll().stream()
                .filter(n -> n.getTitle().equals(requestDto.getTitle()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("공지사항이 저장되지 않았습니다."));
        assertThat(savedNotice.getContent()).isEqualTo(requestDto.getContent());
        assertThat(savedNotice.getUser().getId()).isEqualTo(admin.getId());
    }

    @Test
    @DisplayName("공지사항 삭제 - 성공")
    void testDeleteNotice() {
        // Given
        User admin = userRepository.save(createAdmin("200001", "관리자"));
        Notice notice = noticeRepository.save(createNotice(admin, "테스트 공지사항", "테스트 내용"));

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userType", admin.getType().name());

        // When
        ResponseEntity<?> response = noticeController.deleteNotice(notice.getId(), session);

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue(); // 상태 코드 검증

        // Verify
        boolean noticeExists = noticeRepository.existsById(notice.getId());
        assertThat(noticeExists).isFalse(); // 삭제 여부 검증
    }

    private User createAdmin(String userNum, String userName) {
        return User.builder()
                .userName(userName)
                .userNum(userNum)
                .password("1234")
                .email("aaa@aaa.com")
                .role(Role.ADMIN)
                .type(Type.ADMIN)
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

    private void createNoticesForUser(User user, String noticeTitlePrefix, String noticeContentPrefix) {
        for (int i = 1; i <= 20; i++) {
            Notice notice = createNotice(user, noticeTitlePrefix + " " + i, noticeContentPrefix + " " + i);
            noticeRepository.save(notice); // Notice 저장
        }
    }

    private Notice createNotice(User user, String title, String content) {
        return Notice.builder()
                .user(user)
                .title(title)
                .content(content)
                .createdAt(new Date())
                .build();
    }
}

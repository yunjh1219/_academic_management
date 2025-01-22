package com.example.campushub.notice.repository;

import com.example.campushub.global.config.QueryDslConfig;
import com.example.campushub.notice.domain.Notice;
import com.example.campushub.notice.dto.NoticeListAll;
import com.example.campushub.user.domain.Role;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.domain.Type;
import com.example.campushub.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Import(QueryDslConfig.class)
class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void tearDown() {
        noticeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("공지사항 전체 조회 - 작성자 조건")
    void testFindAllNoticesByCondition_withCreatedBy() {
        // Given
        User userAdmin = userRepository.save(createAdmin("200001", "관리자"));
        User userUser = userRepository.save(createUser("200002", "사용자"));

        // Admin과 User 각각 20개의 공지사항 생성
        createNoticesForUser(userAdmin, "Admin 공지사항", "Admin 내용");
        createNoticesForUser(userUser, "User 공지사항", "User 내용");

        String createdBy = "관리자";
        PageRequest pageable = PageRequest.of(0, 10);

        log.info("조건 설정: createdBy={}, pageable={}", createdBy, pageable);

        // When
        Page<NoticeListAll> result = noticeRepository.findAllByCondition(null, createdBy, pageable);

        log.info("조회 결과: 총 {}개의 공지사항, 현재 페이지 데이터: {}", result.getTotalElements(), result.getContent());

        // Then
        assertThat(result.getTotalElements()).isEqualTo(20); // Admin의 공지사항 개수
        assertThat(result.getContent().get(0).getUserName()).isEqualTo(createdBy);
    }

    @Test
    @DisplayName("공지사항 전체 조회 - 제목 조건")
    void testFindAllNoticesByCondition_withFilter() {
        // Given
        User userAdmin = userRepository.save(createAdmin("200001", "관리자"));
        User userUser = userRepository.save(createUser("200002", "사용자"));

        // Admin과 User 각각 20개의 공지사항 생성
        createNoticesForUser(userAdmin, "Admin 공지사항", "Admin 내용");
        createNoticesForUser(userUser, "User 공지사항", "User 내용");

        String title = "Admin";
        PageRequest pageable = PageRequest.of(0, 10);

        log.info("조건 설정: title={}, pageable={}", title, pageable);

        // When
        Page<NoticeListAll> result = noticeRepository.findAllByCondition(title, null, pageable);

        log.info("조회 결과: 총 {}개의 공지사항, 현재 페이지 데이터: {}", result.getTotalElements(), result.getContent());

        // Then
        assertThat(result.getTotalElements()).isEqualTo(20); // Admin의 공지사항 중 "Admin 공지사항" 포함
        assertThat(result.getContent().get(0).getNoticeTitle()).contains(title);
    }

    @Test
    @DisplayName("공지사항 전체 조회 - 페이징 조건")
    void testFindAllNoticesByCondition_withPagination() {
        // Given
        User userAdmin = userRepository.save(createAdmin("200001", "관리자"));
        User userUser = userRepository.save(createUser("200002", "사용자"));

        // Admin과 User 각각 20개의 공지사항 생성
        createNoticesForUser(userAdmin, "Admin 공지사항", "Admin 내용");
        createNoticesForUser(userUser, "User 공지사항", "User 내용");

        PageRequest pageable = PageRequest.of(1, 10); // 2번째 페이지, 5개씩

        log.info("조건 설정: pageable={}", pageable);

        // When
        Page<NoticeListAll> result = noticeRepository.findAllByCondition(null, null, pageable);

        log.info("조회 결과: 총 {}개의 공지사항, 현재 페이지 데이터: {}", result.getTotalElements(), result.getContent());

        // Then
        assertThat(result.getTotalElements()).isEqualTo(20); // 전체 공지사항 개수
        assertThat(result.getContent().size()).isEqualTo(10); // 2번째 페이지의 데이터 개수
        assertThat(result.getContent().get(5).getNoticeTitle()).isEqualTo("Admin 공지사항 16");
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

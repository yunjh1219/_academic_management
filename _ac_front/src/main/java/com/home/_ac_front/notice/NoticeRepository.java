package com.home._ac_front.notice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // 기본적인 CRUD 메서드가 제공됩니다. (save, findById, findAll, deleteById 등)


}
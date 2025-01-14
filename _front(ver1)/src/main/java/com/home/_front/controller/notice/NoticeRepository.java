package com.home._front.controller.notice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // 기본적인 CRUD 메서드가 제공됩니다. (save, findById, findAll, deleteById 등)


}
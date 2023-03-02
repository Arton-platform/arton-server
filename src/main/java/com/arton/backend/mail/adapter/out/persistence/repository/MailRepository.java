package com.arton.backend.mail.adapter.out.persistence.repository;

import com.arton.backend.mail.adapter.out.persistence.entity.MailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<MailEntity, Long> {
    Page<MailEntity> findAll(Pageable pageable);
}

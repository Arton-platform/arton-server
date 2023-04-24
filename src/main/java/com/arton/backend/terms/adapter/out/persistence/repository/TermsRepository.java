package com.arton.backend.terms.adapter.out.persistence.repository;

import com.arton.backend.terms.adapter.out.persistence.entity.TermsEntity;
import com.arton.backend.terms.domain.TermsCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TermsRepository extends JpaRepository<TermsEntity, Long> {
    List<TermsEntity> findByTermsCase(TermsCase termsCase);
}

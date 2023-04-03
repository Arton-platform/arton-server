package com.arton.backend.terms.adapter.out.persistence.repository;

import com.arton.backend.terms.adapter.out.persistence.entity.TermsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsRepository extends JpaRepository<TermsEntity, Long> {

}

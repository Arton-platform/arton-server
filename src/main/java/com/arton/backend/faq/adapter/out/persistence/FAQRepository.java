package com.arton.backend.faq.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FAQRepository extends JpaRepository<FAQEntity, Long> {
    Optional<List<FAQEntity>> findAllByOrderByCreatedDateDesc();
}

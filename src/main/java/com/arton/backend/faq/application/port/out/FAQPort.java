package com.arton.backend.faq.application.port.out;

import com.arton.backend.faq.adapter.out.persistence.FAQEntity;
import com.arton.backend.faq.domain.FAQ;

import java.util.List;
import java.util.Optional;

public interface FAQPort {
    Optional<List<FAQEntity>> faqList();
    Optional<FAQEntity> findByFaqId(long id);
    Optional<FAQ> findByIdAndUserId(long id, long userId);
}

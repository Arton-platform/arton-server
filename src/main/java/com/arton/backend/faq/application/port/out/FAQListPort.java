package com.arton.backend.faq.application.port.out;

import com.arton.backend.faq.adapter.out.FAQEntity;

import java.util.List;
import java.util.Optional;

public interface FAQListPort {
    Optional<List<FAQEntity>> faqList();
}

package com.arton.backend.faq.application.port.in;

import com.arton.backend.faq.adapter.out.FAQEntity;
import com.arton.backend.faq.domain.FAQ;

import java.util.List;

public interface FAQListUseCase {
    List<FAQ> faqList();
}

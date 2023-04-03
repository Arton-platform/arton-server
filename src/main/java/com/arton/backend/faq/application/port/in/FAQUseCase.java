package com.arton.backend.faq.application.port.in;

import com.arton.backend.faq.domain.FAQ;

import java.util.List;

public interface FAQUseCase {
    List<FAQ> faqList();
    FAQ getFaqById(long id);
}

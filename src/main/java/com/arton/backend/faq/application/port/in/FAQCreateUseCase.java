package com.arton.backend.faq.application.port.in;

import com.arton.backend.faq.application.data.FAQCreateDTO;

public interface FAQCreateUseCase {
    void createFaq(long userId, FAQCreateDTO faqCreateDTO);
}

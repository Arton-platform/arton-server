package com.arton.backend.faq.application.port.in;

public interface FAQDeleteUseCase {
    void deleteFaq(long userId, long faqId);
}

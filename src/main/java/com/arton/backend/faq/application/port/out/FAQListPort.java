package com.arton.backend.faq.application.port.out;

import com.arton.backend.faq.domain.FAQ;

import java.util.List;

public interface FAQListPort {
    List<FAQ> findAllOrderByCreatedTimeDesc();
}

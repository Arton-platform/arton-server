package com.arton.backend.faq.application.service;

import com.arton.backend.faq.adapter.out.FAQEntity;
import com.arton.backend.faq.application.port.in.FAQListUseCase;
import com.arton.backend.faq.application.port.out.FAQListPort;
import com.arton.backend.faq.domain.FAQ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FAQService implements FAQListUseCase {
    FAQListPort faqListPort;

    @Override
    public List<FAQ> faqList() {
       return faqListPort.findAllOrderByCreatedTimeDesc();
    }
}

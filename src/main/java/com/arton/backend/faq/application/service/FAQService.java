package com.arton.backend.faq.application.service;

import com.arton.backend.faq.adapter.out.FAQMapper;
import com.arton.backend.faq.application.port.in.FAQListUseCase;
import com.arton.backend.faq.application.port.out.FAQListPort;
import com.arton.backend.faq.domain.FAQ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FAQService implements FAQListUseCase {
    private final FAQListPort faqListPort;
    private final FAQMapper faqMapper;
    @Override
    public List<FAQ> faqList() {
        return faqListPort.faqList().map(faqEntities -> faqEntities
                        .stream()
                        .map(faqEntity -> faqMapper.toDomain(faqEntity))
                        .collect(Collectors.toList())
                ).orElseGet(ArrayList::new);
    }
}

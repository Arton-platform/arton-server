package com.arton.backend.faq.adapter.out;

import com.arton.backend.faq.application.port.out.FAQListPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FAQPersistenceAdapter implements FAQListPort {
    // port.out 영속계층으로 향하는 포트의 구현 어뎁터
    private final FAQRepository faqRepository;
    private final FAQMapper faqMapper;


    @Override
    public Optional<List<FAQEntity>> faqList() {
        return faqRepository.findAllByOrderByCreatedDateDesc();
    }
}

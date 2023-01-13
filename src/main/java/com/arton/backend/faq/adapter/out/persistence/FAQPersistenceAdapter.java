package com.arton.backend.faq.adapter.out.persistence;

import com.arton.backend.faq.application.port.out.FAQPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FAQPersistenceAdapter implements FAQPort {
    // port.out 영속계층으로 향하는 포트의 구현 어뎁터
    private final FAQRepository faqRepository;

    @Override
    public Optional<List<FAQEntity>> faqList() {
        return faqRepository.findAllByOrderByCreatedDateDesc();
    }

    @Override
    public Optional<FAQEntity> findByFaqId(long id) {
        return faqRepository.findById(id);
    }


}

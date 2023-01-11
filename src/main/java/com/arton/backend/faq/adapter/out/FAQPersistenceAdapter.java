package com.arton.backend.faq.adapter.out;

import com.arton.backend.faq.application.port.out.FAQRepositoryPort;
import com.arton.backend.faq.domain.FAQ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FAQPersistenceAdapter implements FAQRepositoryPort {
    // port.out 영속계층으로 향하는 포트의 구현 어뎁터
    private final FAQRepository faqRepository;
    private final FAQMapper faqMapper;

    @Override
    public List<FAQ> loadAllFAQ() {
        Optional<List<FAQEntity>> faqList = faqRepository.findAllByOrderByCreatedDateDesc();
        return faqList
                .map(faqEntities -> faqMapper.toDomain((FAQEntity) faqEntities))
                .stream()
                .collect(Collectors.toList());

    }
}

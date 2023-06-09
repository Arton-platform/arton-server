package com.arton.backend.faq.adapter.out.persistence;

import com.arton.backend.faq.application.port.out.FAQDeletePort;
import com.arton.backend.faq.application.port.out.FAQPort;
import com.arton.backend.faq.application.port.out.FAQSavePort;
import com.arton.backend.faq.domain.FAQ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FAQPersistenceAdapter implements FAQPort, FAQSavePort, FAQDeletePort {
    // port.out 영속계층으로 향하는 포트의 구현 어뎁터
    private final FAQRepository faqRepository;
    private final FAQMapper faqMapper;

    @Override
    public Optional<List<FAQEntity>> faqList() {
        return faqRepository.findAllByOrderByCreatedDateDesc();
    }

    @Override
    public Optional<FAQEntity> findByFaqId(long id) {
        return faqRepository.findById(id);
    }

    @Override
    public Optional<FAQ> findByIdAndUserId(long id, long userId) {
        return faqRepository.findByFaqIdAndUserId(id, userId).map(faqEntity -> faqMapper.toDomain(faqEntity));
    }

    @Override
    public FAQ save(FAQ faq) {
        return faqMapper.toDomain(faqRepository.save(faqMapper.toEntity(faq)));
    }

    @Override
    public void deleteById(long faqId) {
        faqRepository.deleteById(faqId);
    }
}

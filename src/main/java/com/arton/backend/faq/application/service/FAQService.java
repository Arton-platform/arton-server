package com.arton.backend.faq.application.service;

import com.arton.backend.faq.adapter.out.persistence.FAQMapper;
import com.arton.backend.faq.application.port.in.FAQUseCase;
import com.arton.backend.faq.application.port.out.FAQPort;
import com.arton.backend.faq.domain.FAQ;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FAQService implements FAQUseCase {
    private final FAQPort faqPort;
    private final FAQMapper faqMapper;
    @Override
    public List<FAQ> faqList(){
        return faqPort.faqList().map(faqEntities -> faqEntities
                        .stream()
                        .map(faqEntity -> faqMapper.toDomain(faqEntity))
                        .collect(Collectors.toList())
                ).orElseGet(ArrayList::new);
    }

    @Override
    public FAQ getFaqById(long id) {
        return faqPort.findByFaqId(id)
                .map(faqEntity -> faqMapper.toDomain(faqEntity))
                .orElseThrow(()-> new CustomException("getFaqById", ErrorCode.SELECT_ERROR));
    }
}

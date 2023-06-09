package com.arton.backend.faq.application.service;

import com.arton.backend.faq.adapter.out.persistence.FAQMapper;
import com.arton.backend.faq.application.data.FAQCreateDTO;
import com.arton.backend.faq.application.data.FAQListResponseDTO;
import com.arton.backend.faq.application.data.FAQResponseDTO;
import com.arton.backend.faq.application.port.in.FAQCreateUseCase;
import com.arton.backend.faq.application.port.in.FAQDeleteUseCase;
import com.arton.backend.faq.application.port.in.FAQUseCase;
import com.arton.backend.faq.application.port.out.FAQDeletePort;
import com.arton.backend.faq.application.port.out.FAQPort;
import com.arton.backend.faq.application.port.out.FAQSavePort;
import com.arton.backend.faq.domain.FAQ;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FAQService implements FAQUseCase, FAQCreateUseCase, FAQDeleteUseCase {
    private final FAQPort faqPort;
    private final FAQMapper faqMapper;
    private final FAQSavePort faqSavePort;
    private final FAQDeletePort faqDeletePort;
    private final UserRepositoryPort userRepositoryPort;
    @Override
    public List<FAQ> faqList(){
        return faqPort.faqList().map(faqEntities -> faqEntities
                        .stream()
                        .map(faqEntity -> faqMapper.toDomain(faqEntity))
                        .collect(Collectors.toList())
                ).orElseGet(ArrayList::new);
    }

    @Override
    public List<FAQListResponseDTO> faqListV2() {
        return faqPort.faqList().map(faqEntities -> faqEntities
                .stream()
                .map(FAQListResponseDTO::toDtoFromEntity)
                .collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }

    @Override
    public FAQ getFaqById(long id) {
        return faqPort.findByFaqId(id)
                .map(faqEntity -> faqMapper.toDomain(faqEntity))
                .orElseThrow(()-> new CustomException("getFaqById", ErrorCode.SELECT_ERROR));
    }

    @Override
    public FAQResponseDTO getFaqByIdV2(long id) {
        return faqPort.findByFaqId(id)
                .map(FAQResponseDTO::toDtoFromEntity)
                .orElseThrow(() -> new CustomException("getFaqById", ErrorCode.SELECT_ERROR));
    }

    @Override
    public void createFaq(long userId, FAQCreateDTO faqCreateDTO) {
        User user = userRepositoryPort.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        FAQ faq = faqCreateDTO.toDomainFromDTO();
        faq.setUserId(user.getId());
        faqSavePort.save(faq);
    }

    @Override
    public void deleteFaq(long userId, long faqId) {
        User user = userRepositoryPort.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        FAQ faq = faqPort.findByIdAndUserId(faqId, userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_AUTHORITY.getMessage(), ErrorCode.USER_NOT_AUTHORITY));
        faqDeletePort.deleteById(faqId);
    }
}

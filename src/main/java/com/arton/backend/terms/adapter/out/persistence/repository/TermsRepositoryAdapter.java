package com.arton.backend.terms.adapter.out.persistence.repository;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.terms.adapter.out.persistence.entity.TermsEntity;
import com.arton.backend.terms.adapter.out.persistence.mapper.TermsMapper;
import com.arton.backend.terms.application.port.out.TermsDeletePort;
import com.arton.backend.terms.application.port.out.TermsPort;
import com.arton.backend.terms.application.port.out.TermsSavePort;
import com.arton.backend.terms.domain.Terms;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.terms.adapter.out.persistence.mapper.TermsMapper.*;

@Repository
@RequiredArgsConstructor
public class TermsRepositoryAdapter implements TermsPort, TermsSavePort, TermsDeletePort {
    private final TermsRepository termsRepository;

    @Override
    public void deleteById(Long id) {
        termsRepository.deleteById(id);
    }

    @Override
    public Terms findById(Long id) {
        return toDomain(termsRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.TERMS_NOT_FOUND.getMessage(), ErrorCode.TERMS_NOT_FOUND)));
    }

    @Override
    public List<Terms> findAll() {
        return Optional.ofNullable(termsRepository.findAll()).orElseGet(Collections::emptyList).stream().map(TermsMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Terms save(Terms terms) {
        return toDomain(termsRepository.save(toEntity(terms)));
    }

    @Override
    public List<Terms> saveAll(List<Terms> termsList) {
        List<TermsEntity> termsEntities = termsRepository.saveAll(Optional.ofNullable(termsList).orElseGet(Collections::emptyList).stream().map(TermsMapper::toEntity).collect(Collectors.toList()));
        return Optional.ofNullable(termsEntities).orElseGet(Collections::emptyList).stream().map(TermsMapper::toDomain).collect(Collectors.toList());
    }
}

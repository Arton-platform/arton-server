package com.arton.backend.terms.application.service;

import com.arton.backend.terms.application.data.TermsShowDto;
import com.arton.backend.terms.application.port.in.TermsUseCase;
import com.arton.backend.terms.application.port.out.TermsPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TermsService implements TermsUseCase {
    private final TermsPort termsPort;

    @Override
    public List<TermsShowDto> getTerms() {
         return Optional.ofNullable(termsPort.findAll()).orElseGet(Collections::emptyList).stream().map(TermsShowDto::toDto).collect(Collectors.toList());
    }

}

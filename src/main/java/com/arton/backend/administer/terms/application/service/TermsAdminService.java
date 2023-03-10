package com.arton.backend.administer.terms.application.service;

import com.arton.backend.administer.terms.application.data.TermsAdminCreateDto;
import com.arton.backend.administer.terms.application.data.TermsAdminEditDto;
import com.arton.backend.administer.terms.application.data.TermsAdminResponseDto;
import com.arton.backend.administer.terms.application.port.in.TermsAdminDeleteUseCase;
import com.arton.backend.administer.terms.application.port.in.TermsAdminEditUseCase;
import com.arton.backend.administer.terms.application.port.in.TermsAdminSaveUseCase;
import com.arton.backend.administer.terms.application.port.in.TermsAdminUseCase;
import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.terms.application.port.out.TermsDeletePort;
import com.arton.backend.terms.application.port.out.TermsPort;
import com.arton.backend.terms.application.port.out.TermsSavePort;
import com.arton.backend.terms.domain.Terms;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TermsAdminService implements TermsAdminSaveUseCase, TermsAdminUseCase, TermsAdminDeleteUseCase, TermsAdminEditUseCase {
    @Value("${spring.terms.dir}")
    private String dir;
    private final TermsSavePort termsSavePort;
    private final TermsPort termsPort;
    private final TermsDeletePort termsDeletePort;
    private final FileUploadUtils fileUploadUtils;

    @Override
    public Terms addTerms(TermsAdminCreateDto createDto) {
        Terms terms = createDto.toDomain();
        terms = termsSavePort.save(terms);
        String uploadUrl = fileUploadUtils.uploadHtml(createDto.getFile(), dir + terms.getId());
        terms.setUrl(uploadUrl);
        termsSavePort.save(terms);
        return terms;
    }

    /**
     * 추후 레디스에서 가져오게 변경
     * @return
     */
    @Override
    public List<TermsAdminResponseDto> getTerms() {
        return Optional.ofNullable(termsPort.findAll()).orElseGet(Collections::emptyList).stream().map(TermsAdminResponseDto::toDtoFromDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        Terms terms = termsPort.findById(id);
        termsDeletePort.deleteById(id);
        fileUploadUtils.deleteFile(id, terms.getUrl());
    }

    @Override
    public void editTerms(TermsAdminEditDto editDto) {
        Terms terms = termsPort.findById(editDto.getId());
        fileUploadUtils.deleteFile(terms.getId(), terms.getUrl());
        // update
        String url = fileUploadUtils.uploadHtml(editDto.getFile(), dir + terms.getId());
        terms.setUrl(url);
        terms.setName(editDto.getName());
        termsSavePort.save(terms);
    }
}

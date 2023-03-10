package com.arton.backend.administer.terms.application.service;

import com.arton.backend.administer.terms.application.data.TermsAdminCreateDto;
import com.arton.backend.administer.terms.application.data.TermsAdminResponseDto;
import com.arton.backend.administer.terms.application.port.in.TermsAdminDeleteUseCase;
import com.arton.backend.administer.terms.application.port.in.TermsAdminSaveUseCase;
import com.arton.backend.administer.terms.application.port.in.TermsAdminUseCase;
import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.terms.application.port.out.TermsDeletePort;
import com.arton.backend.terms.application.port.out.TermsPort;
import com.arton.backend.terms.application.port.out.TermsSavePort;
import com.arton.backend.terms.domain.Terms;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TermsAdminService implements TermsAdminSaveUseCase, TermsAdminUseCase, TermsAdminDeleteUseCase {
    @Value("${spring.terms.dir}")
    private String dir;
    private final TermsSavePort termsSavePort;
    private final TermsPort termsPort;
    private final TermsDeletePort termsDeletePort;
    private final FileUploadUtils fileUploadUtils;

    @Override
    public Terms addTerms(TermsAdminCreateDto createDto) {
        MultipartFile file = createDto.getFile();
        String contentType = file.getContentType();
        if (ObjectUtils.isEmpty(file)) {
            throw new CustomException(ErrorCode.FILE_EMPTY.getMessage(), ErrorCode.FILE_EMPTY);
        }
        if (!contentType.equals("text/html")) {
            throw new CustomException(ErrorCode.UNSUPPORTED_MEDIA_ERROR.getMessage(), ErrorCode.UNSUPPORTED_MEDIA_ERROR);
        }
        Terms terms = createDto.toDomain();
        terms = termsSavePort.save(terms);

        System.out.println("contentType = " + contentType);
//        String uploadUrl = fileUploadUtils.upload(createDto.getMultipartFile(), dir + savedTerms.getId());
        String uploadUrl = "temp";
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
        termsDeletePort.deleteById(id);
    }
}

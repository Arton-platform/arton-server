package com.arton.backend.administer.terms.application.port.in;

import com.arton.backend.administer.terms.application.data.TermsAdminResponseDto;
import com.arton.backend.terms.domain.Terms;

import java.util.List;

public interface TermsAdminUseCase {
    List<TermsAdminResponseDto> getTerms();
}

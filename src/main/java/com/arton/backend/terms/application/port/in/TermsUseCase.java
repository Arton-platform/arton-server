package com.arton.backend.terms.application.port.in;

import com.arton.backend.terms.application.data.TermsShowDto;

import java.util.List;

public interface TermsUseCase {
    List<TermsShowDto> getTerms();
}

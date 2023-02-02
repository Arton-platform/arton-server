package com.arton.backend.auth.application.port.in;

import com.arton.backend.auth.application.data.TermsShowDto;

import java.util.List;

public interface TermsUseCase {
    List<TermsShowDto> getTerms();
}

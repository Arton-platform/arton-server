package com.arton.backend.auth.application.port.in;

import java.util.List;

public interface TermsUseCase {
    List<TermsShowDto> getTerms();
}

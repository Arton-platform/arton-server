package com.arton.backend.administer.terms.application.port.in;

import com.arton.backend.administer.terms.application.data.TermsAdminCreateDto;
import com.arton.backend.administer.terms.application.data.TermsAdminCreateDtoV2;
import com.arton.backend.terms.domain.Terms;

public interface TermsAdminSaveUseCase {
    Terms addTerms(TermsAdminCreateDto createDto);
    Terms addTermsV2(TermsAdminCreateDtoV2 createDto);
}

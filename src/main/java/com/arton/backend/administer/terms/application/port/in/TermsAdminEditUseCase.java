package com.arton.backend.administer.terms.application.port.in;

import com.arton.backend.administer.terms.application.data.TermsAdminEditDto;

public interface TermsAdminEditUseCase {
    void editTerms(TermsAdminEditDto editDto);
}

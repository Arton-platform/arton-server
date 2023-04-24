package com.arton.backend.administer.terms.application.port.in;

import com.arton.backend.administer.terms.application.data.TermsAdminEditDto;
import com.arton.backend.administer.terms.application.data.TermsAdminEditDtoV2;

public interface TermsAdminEditUseCase {
    void editTerms(TermsAdminEditDto editDto);
    void editTermsV2(TermsAdminEditDtoV2 editDto);
}

package com.arton.backend.administer.terms.application.port.in;

public interface TermsAdminDeleteUseCase {
    void deleteById(Long id);
    void deleteByIdV2(Long id);
}

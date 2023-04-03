package com.arton.backend.terms.application.port.out;


import com.arton.backend.terms.domain.Terms;

import java.util.List;

public interface TermsPort {
    Terms findById(Long id);
    List<Terms> findAll();
}

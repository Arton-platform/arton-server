package com.arton.backend.terms.application.port.out;

import com.arton.backend.terms.domain.Terms;

import java.util.List;

public interface TermsSavePort {
    Terms save(Terms terms);
    List<Terms> saveAll(List<Terms> termsList);
}

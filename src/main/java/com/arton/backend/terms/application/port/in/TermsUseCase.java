package com.arton.backend.terms.application.port.in;

import com.arton.backend.terms.application.data.TermsShowDto;
import com.arton.backend.terms.application.data.TermsShowDtoV2;

import java.util.List;

public interface TermsUseCase {
    List<TermsShowDto> getTerms();
    /**
     * 0424 content 추가 의견 반영 버전
     * @return
     */
    List<TermsShowDtoV2> getTermsV2();
}

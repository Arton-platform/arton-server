package com.arton.backend.administer.search.adapter.in;

import com.arton.backend.administer.search.application.port.in.KeywordDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * 관리자만 접근 가능.
 */
@Controller
//@Secured("ADMIN")
@RequiredArgsConstructor
public class AdminSearchController {
    private final KeywordDeleteUseCase keywordDeleteUseCase;
}

package com.arton.backend.administer.terms.application.data;

import com.arton.backend.terms.domain.Terms;
import com.arton.backend.terms.domain.TermsCase;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * 약관 등록 폼
 */
@Data
@NoArgsConstructor
public class TermsAdminCreateDtoV2 {
    private String name;
    private TermsCase termsCase;
    private String content;

    public Terms toDomain(){
        return Terms.builder()
                .name(name)
                .termsCase(termsCase)
                .content(content)
                .build();
    }
}

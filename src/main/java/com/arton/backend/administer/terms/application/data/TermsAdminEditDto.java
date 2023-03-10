package com.arton.backend.administer.terms.application.data;

import com.arton.backend.terms.domain.Terms;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * 약관 등록 폼
 */
@Data
@NoArgsConstructor
public class TermsAdminEditDto {
    private Long id;
    private String name;
    private MultipartFile file;

    public Terms toDomain(){
        return Terms.builder()
                .id(id)
                .name(name)
                .build();
    }
}

package com.arton.backend.auth.application.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class KeyAlgDTO {
    private String kty;
    private String kid;
    private String use;
    private String alg;
    private String n;
    private String e;

    @Builder
    public KeyAlgDTO(String kty, String kid, String use, String alg, String n, String e) {
        this.kty = kty;
        this.kid = kid;
        this.use = use;
        this.alg = alg;
        this.n = n;
        this.e = e;
    }
}

package com.arton.backend.auth.application.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class KeyAlgResponseDTO {
    private List<KeyAlgDTO> keys;

    @Builder
    public KeyAlgResponseDTO(List<KeyAlgDTO> keys) {
        this.keys = keys;
    }
}

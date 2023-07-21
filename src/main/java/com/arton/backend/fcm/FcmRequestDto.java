package com.arton.backend.fcm;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FcmRequestDto {
    private Long userId;
    private String title;
    private String body;

    @Builder
    public FcmRequestDto(Long userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
}

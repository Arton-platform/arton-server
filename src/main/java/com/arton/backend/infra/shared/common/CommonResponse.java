package com.arton.backend.infra.shared.common;

import lombok.*;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class CommonResponse {
    private String message;
    private Integer status;
}

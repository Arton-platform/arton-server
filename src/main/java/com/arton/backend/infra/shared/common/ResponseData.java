package com.arton.backend.infra.shared.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseData<T> extends CommonResponse {
    private T data;

    public ResponseData(String message, int status, T data) {
        super(message, status);
        this.data = data;
    }

    public ResponseData(String message, int status) {
        super(message, status);
    }
}

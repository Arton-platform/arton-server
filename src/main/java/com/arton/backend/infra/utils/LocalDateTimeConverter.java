package com.arton.backend.infra.utils;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter {
    public static LocalDateTime strToDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            return LocalDateTime.parse(date, formatter);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.PARAMETER_NOT_VALID.getErrorCode(), ErrorCode.PARAMETER_NOT_VALID);
        }
    }
}

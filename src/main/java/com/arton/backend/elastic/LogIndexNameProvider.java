package com.arton.backend.elastic;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LogIndexNameProvider {
    public String timeSuffix() {
        return LocalDate.now().toString().replaceAll("-",".");
    }
}

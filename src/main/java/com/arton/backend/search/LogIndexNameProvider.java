package com.arton.backend.search;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LogIndexNameProvider {
    public String timeSuffix() {
        return LocalDate.now().toString().replaceAll("-",".");
    }
}

package com.arton.backend.review.application.port.in;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class MyPageReviewDtoTest {

    @Test
    void dateFormatTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        String format = LocalDateTime.now().format(formatter);
        System.out.println("format = " + format);
    }
}
package com.arton.backend.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy")
@RequiredArgsConstructor
public class DummyController {
    private final DummyService dummyService;

    @GetMapping("/announcement")
    public ResponseEntity createDummyAnnouncements() {
        dummyService.createDummyAnnouncements();
        return ResponseEntity.ok("더미 공지사항 생성 완료...");
    }
    @GetMapping("/faq")
    public ResponseEntity createDummyFAQs() {
        dummyService.createDummyFAQ();
        return ResponseEntity.ok("더미 faq 생성 완료...");
    }

}

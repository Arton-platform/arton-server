package com.arton.backend.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController("/dummy")
@RequiredArgsConstructor
public class DummyController {
    private final DummyService dummyService;
}

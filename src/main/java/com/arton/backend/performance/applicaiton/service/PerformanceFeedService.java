package com.arton.backend.performance.applicaiton.service;

import com.arton.backend.performance.applicaiton.port.out.PerformanceFeedPort;
import com.arton.backend.performance.domain.Performance;
import org.springframework.stereotype.Service;

import com.arton.backend.performance.applicaiton.port.in.PerformanceFeedUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PerformanceFeedService implements PerformanceFeedUseCase {

    private final PerformanceFeedPort performanceFeedPort;

    @Override
    public List<Performance> getAllPerformance() {
        performanceFeedPort.getAllPerformance()
                .orElseGet(ArrayList::new)
                .stream()
                .forEach(System.out::println);
        return null;
    }
}

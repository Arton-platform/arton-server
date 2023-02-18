package com.arton.backend.performance.applicaiton.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureWebMvc
class PerformanceServiceTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    PerformanceService performanceService;

    @Test
    void getSpecificPerformanceTest() throws Exception {
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get("/performance/1")).andReturn().getResponse().getContentAsString();
        System.out.println("contentAsString = " + contentAsString);
    }
}
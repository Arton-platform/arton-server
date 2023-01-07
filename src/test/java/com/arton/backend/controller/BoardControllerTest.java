package com.arton.backend.controller;

import com.arton.backend.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BoardService boardService;

    @Test
    void faqList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/faq"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
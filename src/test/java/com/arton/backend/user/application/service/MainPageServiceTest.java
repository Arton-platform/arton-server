package com.arton.backend.user.application.service;

import com.arton.backend.auth.application.data.LoginRequestDto;
import com.arton.backend.user.application.data.MainPageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@AutoConfigureMockMvc
class MainPageServiceTest {

    @Autowired
    MainPageService mainPageService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getMainPageTest() {
        MainPageDto mainPage = mainPageService.getMainPage(1L, 0, 10);
        System.out.println("mainPage = " + mainPage);
    }

    @Test
    void mvcTest() throws Exception {
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("j6731000@gmail.com")
                .password("temp")
                .build();

        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .content(objectMapper.writeValueAsString(loginRequestDto))
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult mvcResult = perform.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("contentAsString = " + contentAsString);
        String accessToken = objectMapper.readTree(contentAsString).get("accessToken").asText();

        MvcResult home = mockMvc.perform(MockMvcRequestBuilders.get("/home").header("Authorization", "Bearer " + accessToken)).andReturn();
        String contentAsString1 = home.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("contentAsString1 = " + contentAsString1);
    }
}
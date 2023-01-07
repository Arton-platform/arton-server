package com.arton.backend.auth.adapter.in;

import com.arton.backend.auth.application.port.in.SignupRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Description("이미지 없이 회원가입")
    @Test
    void artonSignupTest() throws Exception {
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .ageRange(10)
                .email("abc123@naver.com")
                .password("thskan11")
                .checkPassword("thskan11")
                .marketingAgreement("Y")
                .gender("MALE")
                .nickname("nick")
                .build();
        String content = objectMapper.writeValueAsString(signupRequestDto);
        System.out.println("content = " + content);
        MockMultipartFile json = new MockMultipartFile("signupRequestDto", "jsondata", "application/json", content.getBytes(StandardCharsets.UTF_8));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/auth/signup")
                        .file(json)
                        .content(objectMapper.writeValueAsString(signupRequestDto))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                        .andExpect(status().isOk());
    }
}
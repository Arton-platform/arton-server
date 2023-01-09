package com.arton.backend.auth.adapter.in;

import com.arton.backend.auth.application.port.in.LoginRequestDto;
import com.arton.backend.auth.application.port.in.SignupRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
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
                .termsAgree("Y")
                .gender("MALE")
                .nickname("nick")
                .build();
        String content = objectMapper.writeValueAsString(signupRequestDto);
        System.out.println("content = " + content);
        MockMultipartFile json = new MockMultipartFile("signupRequestDto", "jsondata", "application/json", content.getBytes(StandardCharsets.UTF_8));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/auth/signup")
                        .file(json)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                        .andExpect(status().isOk());
    }

    @Description("회원가입 with 이미지")
    @Test
    void artonSignupImageTest() throws Exception {
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .ageRange(10)
                .email("abc123@naver.com")
                .password("thskan11")
                .checkPassword("thskan11")
                .termsAgree("Y")
                .gender("MALE")
                .nickname("nick")
                .build();
        String content = objectMapper.writeValueAsString(signupRequestDto);
        System.out.println("content = " + content);
        MockMultipartFile image = new MockMultipartFile("image", "static/image/dog.jpg", "image/jpeg", "<<jpeg data>>".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile json = new MockMultipartFile("signupRequestDto", "jsondata", "application/json", content.getBytes(StandardCharsets.UTF_8));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/auth/signup")
                        .file(json)
                        .file(image)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }

    @Description("회원가입 패스워드 불일치")
    @Test
    void passwordNotMatchTest() throws Exception {
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .ageRange(10)
                .email("abc123@naver.com")
                .password("thskan11")
                .checkPassword("thskan1221")
                .termsAgree("Y")
                .gender("MALE")
                .nickname("nick")
                .build();
        String content = objectMapper.writeValueAsString(signupRequestDto);
        System.out.println("content = " + content);
        MockMultipartFile image = new MockMultipartFile("image", "static/image/dog.jpg", "image/jpeg", "<<jpeg data>>".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile json = new MockMultipartFile("signupRequestDto", "jsondata", "application/json", content.getBytes(StandardCharsets.UTF_8));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/auth/signup")
                        .file(json)
                        .file(image)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }

    @Description("회원가입 이메일 중복")
    @Test
    void emailDupTest() throws Exception {
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .ageRange(10)
                .email("abc123@naver.com")
                .password("thskan11")
                .checkPassword("thskan11")
                .termsAgree("Y")
                .gender("MALE")
                .nickname("nick")
                .build();
        String content = objectMapper.writeValueAsString(signupRequestDto);
        System.out.println("content = " + content);
        MockMultipartFile image = new MockMultipartFile("image", "static/image/dog.jpg", "image/jpeg", "<<jpeg data>>".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile json = new MockMultipartFile("signupRequestDto", "jsondata", "application/json", content.getBytes(StandardCharsets.UTF_8));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/auth/signup")
                        .file(json)
                        .file(image)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        SignupRequestDto signupRequestDto2 = SignupRequestDto.builder()
                .ageRange(10)
                .email("abc123@naver.com")
                .password("aaa123")
                .checkPassword("aaa123")
                .termsAgree("Y")
                .gender("MALE")
                .nickname("aaa")
                .build();
        String content2 = objectMapper.writeValueAsString(signupRequestDto2);
        System.out.println("content2 = " + content2);
        MockMultipartFile image2 = new MockMultipartFile("image", "static/image/dog.jpg", "image/jpeg", "<<jpeg data>>".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile json2 = new MockMultipartFile("signupRequestDto", "jsondata", "application/json", content2.getBytes(StandardCharsets.UTF_8));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/auth/signup")
                        .file(json2)
                        .file(image2)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is4xxClientError());
    }

    @Description("로그인 성공 테스트")
    @Test
    void loginTest() throws Exception {
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .ageRange(10)
                .email("abc123@naver.com")
                .password("thskan11")
                .checkPassword("thskan11")
                .termsAgree("Y")
                .gender("MALE")
                .nickname("nick")
                .build();
        String content = objectMapper.writeValueAsString(signupRequestDto);
        System.out.println("content = " + content);
        MockMultipartFile image = new MockMultipartFile("image", "static/image/dog.jpg", "image/jpeg", "<<jpeg data>>".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile json = new MockMultipartFile("signupRequestDto", "jsondata", "application/json", content.getBytes(StandardCharsets.UTF_8));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/auth/signup")
                        .file(json)
                        .file(image)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("abc123@naver.com")
                .password("thskan11")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .content(objectMapper.writeValueAsString(loginRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
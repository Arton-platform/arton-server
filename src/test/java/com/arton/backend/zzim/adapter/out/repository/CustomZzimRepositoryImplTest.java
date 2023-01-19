package com.arton.backend.zzim.adapter.out.repository;

import com.arton.backend.artist.adapter.out.repository.ArtistEntity;
import com.arton.backend.artist.adapter.out.repository.ArtistRepository;
import com.arton.backend.auth.application.port.in.SignupRequestDto;
import com.arton.backend.user.adapter.out.repository.UserEntity;
import com.arton.backend.user.adapter.out.repository.UserRepository;
import com.arton.backend.zzim.domain.ArtistZzim;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomZzimRepositoryImplTest {
    @Autowired
    ArtistZzimRepository artistZzimRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Description("유저의 찜 리스트 가져오기")
    @Test
    void name() throws Exception {
        List<Long> ids = artistRepository.findAll().stream().filter(artistEntity -> artistEntity.getId() % 2 == 0).map(ArtistEntity::getId).collect(Collectors.toList());
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .ageRange(10)
                .email("abc123@naver.com")
                .password("thskan11")
                .checkPassword("thskan11")
                .termsAgree("Y")
                .gender("MALE")
                .nickname("nick")
                .artists(ids)
                .performances(new ArrayList<>())
                .build();
        String content = objectMapper.writeValueAsString(signupRequestDto);
        System.out.println("content = " + content);
        MockMultipartFile json = new MockMultipartFile("signupRequestDto", "jsondata", "application/json", content.getBytes(StandardCharsets.UTF_8));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/auth/signup")
                        .file(json)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        // 유저의 찜리스트
        UserEntity user = userRepository.findByEmail("abc123@naver.com").get();
        List<ArtistZzim> usersFavoriteArtists = artistZzimRepository.getUsersFavoriteArtists(user.getId());
        for (ArtistZzim usersFavoriteArtist : usersFavoriteArtists) {
            System.out.println("usersFavoriteArtist = " + usersFavoriteArtist);
        }

    }
}
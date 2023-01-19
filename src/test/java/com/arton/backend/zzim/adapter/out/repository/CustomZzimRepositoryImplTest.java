package com.arton.backend.zzim.adapter.out.repository;

import com.arton.backend.artist.adapter.out.repository.ArtistEntity;
import com.arton.backend.artist.adapter.out.repository.ArtistRepository;
import com.arton.backend.auth.application.port.in.SignupRequestDto;
import com.arton.backend.auth.application.service.CustomUserDetailsService;
import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.adapter.out.repository.PerformanceRepository;
import com.arton.backend.user.adapter.out.repository.UserEntity;
import com.arton.backend.user.adapter.out.repository.UserRepository;
import com.arton.backend.zzim.application.port.in.ZzimDeleteDto;
import com.arton.backend.zzim.domain.ArtistZzim;
import com.arton.backend.zzim.domain.PerformanceZzim;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomZzimRepositoryImplTest {
    @Autowired
    ArtistZzimRepository artistZzimRepository;
    @Autowired
    PerformanceZzimRepository performanceZzimRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    PerformanceRepository performanceRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext context;

    UserDetails userDetails;
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Description("유저의 Artist 찜 리스트 가져오기")
    @Test
    void getArtistZzimTest() throws Exception {
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
        mockMvc.perform(multipart("/auth/signup")
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

    @Description("유저의 공연 리스트 가져오기")
    @Test
    void getPerformanceZzimTest() throws Exception {
        List<Long> ids = performanceRepository.findAll().stream().filter(performance -> performance.getId() % 2 == 0).map(PerformanceEntity::getId).collect(Collectors.toList());
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .ageRange(10)
                .email("abc123@naver.com")
                .password("thskan11")
                .checkPassword("thskan11")
                .termsAgree("Y")
                .gender("MALE")
                .nickname("nick")
                .performances(ids)
                .artists(new ArrayList<>())
                .build();
        String content = objectMapper.writeValueAsString(signupRequestDto);
        System.out.println("content = " + content);
        MockMultipartFile json = new MockMultipartFile("signupRequestDto", "jsondata", "application/json", content.getBytes(StandardCharsets.UTF_8));
        mockMvc.perform(multipart("/auth/signup")
                        .file(json)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        // 유저의 찜리스트
        UserEntity user = userRepository.findByEmail("abc123@naver.com").get();
        List<PerformanceZzim> usersFavoritePerformances = performanceZzimRepository.getUsersFavoritePerformances(user.getId());
        for (PerformanceZzim userFavoritePerformance : usersFavoritePerformances) {
            System.out.println("userFavoritePerformance = " + userFavoritePerformance);
        }
    }

    @Description("유저의 공연 + 아티스 찜 리스트 가져오기")
    @Test
    void getZzimTest() throws Exception {
        List<Long> artistIds = artistRepository.findAll().stream().filter(artistEntity -> artistEntity.getId() % 2 == 0).map(ArtistEntity::getId).collect(Collectors.toList());
        List<Long> ids = performanceRepository.findAll().stream().filter(performance -> performance.getId() % 2 == 0).map(PerformanceEntity::getId).collect(Collectors.toList());
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .ageRange(10)
                .email("abc123@naver.com")
                .password("thskan11")
                .checkPassword("thskan11")
                .termsAgree("Y")
                .gender("MALE")
                .nickname("nick")
                .performances(ids)
                .artists(artistIds)
                .build();
        String content = objectMapper.writeValueAsString(signupRequestDto);
        System.out.println("content = " + content);
        MockMultipartFile json = new MockMultipartFile("signupRequestDto", "jsondata", "application/json", content.getBytes(StandardCharsets.UTF_8));
        MvcResult mvcResult = mockMvc.perform(multipart("/auth/signup")
                        .file(json)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        System.out.println("response = " + response);

        // 유저의 찜리스트
        UserEntity user = userRepository.findByEmail("abc123@naver.com").get();
        List<PerformanceZzim> usersFavoritePerformances = performanceZzimRepository.getUsersFavoritePerformances(user.getId());
        for (PerformanceZzim userFavoritePerformance : usersFavoritePerformances) {
            System.out.println("userFavoritePerformance = " + userFavoritePerformance);
        }
        List<ArtistZzim> usersFavoriteArtists = artistZzimRepository.getUsersFavoriteArtists(user.getId());
        for (ArtistZzim usersFavoriteArtist : usersFavoriteArtists) {
            System.out.println("usersFavoriteArtist = " + usersFavoriteArtist);
        }
    }

    @Description("유저의 공연 + 아티스 찜 리스트 편집하기")
    @Test
    void deleteZzimTest() throws Exception {
        List<Long> artistIds = artistRepository.findAll().stream().filter(artistEntity -> artistEntity.getId() % 2 == 0).map(ArtistEntity::getId).collect(Collectors.toList());
        List<Long> ids = performanceRepository.findAll().stream().filter(performance -> performance.getId() % 2 == 0).map(PerformanceEntity::getId).collect(Collectors.toList());
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .ageRange(10)
                .email("abc123@naver.com")
                .password("thskan11")
                .checkPassword("thskan11")
                .termsAgree("Y")
                .gender("MALE")
                .nickname("nick")
                .performances(ids)
                .artists(artistIds)
                .build();
        String content = objectMapper.writeValueAsString(signupRequestDto);
        System.out.println("content = " + content);
        MockMultipartFile json = new MockMultipartFile("signupRequestDto", "jsondata", "application/json", content.getBytes(StandardCharsets.UTF_8));
        MvcResult mvcResult = mockMvc.perform(multipart("/auth/signup")
                        .file(json)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        System.out.println("response = " + response);

        // 유저의 찜리스트
        UserEntity user = userRepository.findByEmail("abc123@naver.com").get();
        List<PerformanceZzim> usersFavoritePerformances = performanceZzimRepository.getUsersFavoritePerformances(user.getId());
        for (PerformanceZzim userFavoritePerformance : usersFavoritePerformances) {
            System.out.println("userFavoritePerformance = " + userFavoritePerformance);
        }
        List<ArtistZzim> usersFavoriteArtists = artistZzimRepository.getUsersFavoriteArtists(user.getId());
        for (ArtistZzim usersFavoriteArtist : usersFavoriteArtists) {
            System.out.println("usersFavoriteArtist = " + usersFavoriteArtist);
        }

        // 아티스트 찜 편집
        artistZzimRepository.deleteUsersFavoriteArtists(user.getId(), artistIds.subList(0, artistIds.size()/2));
        List<ArtistZzim> afterUsersFavoriteArtists = artistZzimRepository.getUsersFavoriteArtists(user.getId());
        for (ArtistZzim afterArtist : afterUsersFavoriteArtists) {
            System.out.println("afterArtist = " + afterArtist);
        }
        // 공연 편집
        performanceZzimRepository.deleteUsersFavoritePerformances(user.getId(), ids.subList(0, ids.size()/2));
        List<PerformanceZzim> afterUsersFavoritePerformances = performanceZzimRepository.getUsersFavoritePerformances(user.getId());
        for (PerformanceZzim afterPerformance : afterUsersFavoritePerformances) {
            System.out.println("afterPerformance = " + afterPerformance);
        }

        assertThat(afterUsersFavoriteArtists.size()).isNotEqualTo(usersFavoriteArtists.size());
        assertThat(afterUsersFavoritePerformances.size()).isNotEqualTo(usersFavoritePerformances.size());
    }

    @Description("유저의 아티스트 찜 초기화")
    @Test
    void deleteAllArtistZzimTest() throws Exception {
        List<Long> artistIds = artistRepository.findAll().stream().filter(artistEntity -> artistEntity.getId() % 2 == 0).map(ArtistEntity::getId).collect(Collectors.toList());
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .ageRange(10)
                .email("abc123@naver.com")
                .password("thskan11")
                .checkPassword("thskan11")
                .termsAgree("Y")
                .gender("MALE")
                .nickname("nick")
                .performances(new ArrayList<>())
                .artists(artistIds)
                .build();
        String content = objectMapper.writeValueAsString(signupRequestDto);
        System.out.println("content = " + content);
        MockMultipartFile json = new MockMultipartFile("signupRequestDto", "jsondata", "application/json", content.getBytes(StandardCharsets.UTF_8));
        MvcResult mvcResult = mockMvc.perform(multipart("/auth/signup")
                        .file(json)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        System.out.println("response = " + response);

        // 유저의 찜리스트
        UserEntity user = userRepository.findByEmail("abc123@naver.com").get();

        List<ArtistZzim> usersFavoriteArtists = artistZzimRepository.getUsersFavoriteArtists(user.getId());
        for (ArtistZzim usersFavoriteArtist : usersFavoriteArtists) {
            System.out.println("usersFavoriteArtist = " + usersFavoriteArtist);
        }

        // 아티스트 찜 편집
        artistZzimRepository.deleteAllByUserId(user.getId());
        List<ArtistZzimEntity> allByUserId = artistZzimRepository.findAllByUserId(user.getId());
        assertThat(allByUserId.size()).isEqualTo(0);
    }

    @Transactional
    @Commit
    @Description("유저의 찜 삭제")
    @Test
    void deleteEditZzimTest() throws Exception {
        // security apply
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        List<Long> ids = performanceRepository.findAll().stream().filter(performance -> performance.getId() % 2 == 0).map(PerformanceEntity::getId).collect(Collectors.toList());
        List<Long> artistIds = artistRepository.findAll().stream().filter(artistEntity -> artistEntity.getId() % 2 == 0).map(ArtistEntity::getId).collect(Collectors.toList());
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .ageRange(10)
                .email("abc123@naver.com")
                .password("thskan11")
                .checkPassword("thskan11")
                .termsAgree("Y")
                .gender("MALE")
                .nickname("nick")
                .performances(ids)
                .artists(artistIds)
                .build();
        String content = objectMapper.writeValueAsString(signupRequestDto);
        MockMultipartFile json = new MockMultipartFile("signupRequestDto", "jsondata", "application/json", content.getBytes(StandardCharsets.UTF_8));
        MvcResult mvcResult = mockMvc.perform(multipart("/auth/signup")
                        .file(json)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        // 유저의 찜리스트
        UserEntity user = userRepository.findByEmail("abc123@naver.com").get();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(String.valueOf(user.getId()));

        ZzimDeleteDto deleteDto = ZzimDeleteDto.builder().artists(artistIds.subList(0, artistIds.size() / 2))
                .performances(ids.subList(0, ids.size()/2)).build();
        mockMvc.perform(post("/zzim/cancel").with(SecurityMockMvcRequestPostProcessors.user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteDto)))
                .andExpect(status().isNoContent());
    }


}
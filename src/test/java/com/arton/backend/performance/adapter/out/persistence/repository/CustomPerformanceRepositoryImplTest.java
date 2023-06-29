package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.auth.application.data.LoginRequestDto;
import com.arton.backend.auth.application.data.SignupRequestDto;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.performance.domain.Performance;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional(readOnly = true)
@SpringBootTest
@AutoConfigureMockMvc
class CustomPerformanceRepositoryImplTest {
    @Autowired
    PerformanceRepository performanceRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getReviewTest() throws Exception {
        String dto = "{\"title\":\"싸이흠뻑쇼 SUMMERSWAG2023 - 서울\",\"link\": \"https://tickets.interpark.com/goods/23007049\",\"musicalDateTime\":\"2023.06.30~2023.07.02\",\"startDate\":\"2023.06.30\",\"endDate\":\"2023.07.02\",\"ticketOpenDate\":\"\",\"ticketEndDate\":\"\",\"place\":\"잠실 종합운동장 올림픽주경기장\",\"runningTime\":\"0\",\"limitAge\":\"0\",\"description\":\"https://ticketimage.interpark.com/Play/image/etc/23/23007049-06.jpg\",\"imageUrl\":\"https://ticketimage.interpark.com/Play/image/large/23/23007049_p.gif\",\"performanceType\":\"콘서트\",\"artists\":[],\"grades\":[{\"gradeName\":\"스탠딩SR\",\"price\":\"165000\"},{\"gradeName\":\"스탠딩R\",\"price\":\"154000\"},{\"gradeName\":\"지정석SR\",\"price\":\"165000\"},{\"gradeName\":\"지정석R\",\"price\":\"154000\"},{\"gradeName\":\"지정석S\",\"price\":\"132000\"}]}";
//        String dto = "{\"title\":\"싸이흠뻑쇼 SUMMERSWAG2023 - 서울\",\"link\": \"https://tickets.interpark.com/goods/23007049\",\"musicalDateTime\":\"2023.06.30~2023.07.02\",\"startDate\":\"2023.06.30\",\"endDate\":\"2023.07.02\",\"ticketOpenDate\":\"\",\"ticketEndDate\":\"\",\"place\":\"잠실 종합운동장 올림픽주경기장\",\"runningTime\":\"0\",\"limitAge\":\"0\",\"description\":\"https://ticketimage.interpark.com/Play/image/etc/23/23007049-06.jpg\",\"imageUrl\":\"https://ticketimage.interpark.com/Play/image/large/23/23007049_p.gif\",\"performanceType\":\"콘서트\",\"artists\":[{\"name\":\"싸이\",\"profileImageUrl\":\"https://ticketimage.interpark.com/PlayDictionary/DATA/PlayDic/PlayDicUpload/040004/08/01/0400040801_5044_021006.gif\",\"age\":\"0\",\"snsId\":\"\"}],\"grades\":[{\"gradeName\":\"스탠딩SR\",\"price\":\"165000\"},{\"gradeName\":\"스탠딩R\",\"price\":\"154000\"},{\"gradeName\":\"지정석SR\",\"price\":\"165000\"},{\"gradeName\":\"지정석R\",\"price\":\"154000\"},{\"gradeName\":\"지정석S\",\"price\":\"132000\"}]}";
        String signup = "{\"email\":\"admin0554@gmail.com\",\"password\":\"admin##2\",\"checkPassword\":\"admin##2\",\"nickname\":\"admin\",\"gender\":\"MALE\",\"ageRange\":40,\"termsAgree\":\"Y\"}";
        // signup
        mockMvc.perform(MockMvcRequestBuilders.post("/web/regist/v2")
                        .content(signup)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        String login = "{\"email\":\"admin0554@gmail.com\",\"password\":\"admin##2\"}";
        // login
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.post("/web/login")
                        .content(login)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(contentAsString);
        // get accessToken
        String accessToken = jsonNode.get("accessToken").asText();
        System.out.println("accessToken = " + accessToken);

        // add

        mockMvc.perform(MockMvcRequestBuilders.post("/performance/crawler")
                        .header("Authorization", "Bearer " + accessToken)
                        .content(dto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // get
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/performance/1")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println("result = " + result);
    }

    /**
     * getPerformanceByStartDateASC 기능 테스트
     */
    @Test
    void getPerformanceByStartDateASCTest() {
        List<Performance> performanceByStartDateASC = performanceRepository.getPerformanceByStartDateASC(0, 10);
        List<PerformanceEntity> collect = performanceRepository.findAll().stream().filter(performanceEntity -> performanceEntity.getStartDate().isAfter(LocalDateTime.now())).collect(Collectors.toList());
        List<PerformanceEntity> total = performanceRepository.findAll().stream().collect(Collectors.toList());
        if (performanceByStartDateASC != null && collect != null) {
            System.out.println("collect = " + collect.size());
            System.out.println("performanceByStartDateASC = " + performanceByStartDateASC.size());
            System.out.println("total = " + total.size());
            Assertions.assertThat(performanceByStartDateASC.size()).isEqualTo(collect.size());
        }
    }

    @Test
    void getPerformanceByEndDateASCTest() {
        List<Performance> performances = performanceRepository.getPerformanceByEndDateASC(0, 10);
        List<PerformanceEntity> collect = performanceRepository.findAll().stream().filter(performanceEntity -> performanceEntity.getStartDate().isAfter(LocalDateTime.now())).collect(Collectors.toList());
        if (performances != null && collect != null) {
            System.out.println("collect = " + collect.size());
            System.out.println("performanceByStartDateASC = " + performances.size());
            int idx = 1;
            for (Performance performance : performances) {
                System.out.println("performance " + (idx++) +" = " + performance);
            }
            Assertions.assertThat(performances.size()).isEqualTo(collect.size());
        }
    }

    @Test
    void getPopularPerformanceTest() {
        List<Performance> performances = performanceRepository.getPopularPerformances(0, 10);
        List<PerformanceEntity> collect = performanceRepository.findAll().stream().filter(performanceEntity -> performanceEntity.getStartDate().isAfter(LocalDateTime.now())).collect(Collectors.toList());
        if (performances != null && collect != null) {
            System.out.println("collect = " + collect.size());
            System.out.println("performanceByStartDateASC = " + performances.size());
            int idx = 1;
            for (Performance performance : performances) {
                System.out.println("performance " + (idx++) +" = " + performance);
            }
            Assertions.assertThat(performances.size()).isEqualTo(collect.size());
        }
    }

}
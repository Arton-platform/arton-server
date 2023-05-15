package com.arton.backend.performance.applicaiton.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 문화공공데이터 광장의 API 테스트입니다.
 */
@SpringBootTest
public class PerformanceCollectApiTest {

    private final String MY_KEY = "ac21a713-af02-420a-b081-526498e634ea"; // 문체부기관공연정보
    private final String DATA_GG_KEY = "RZEQR5lqa3UsV9aJtt1NSG6%2FahORfrCIhL6fMPvSQnc8krNu28Hoi%2Bgc3hb7ZnGOxO1F1%2B5rqAGN74%2BKqiFzbw%3D%3D"; // 한국문화정보원
    private final String DATA_GG_KEY_DECODED = "RZEQR5lqa3UsV9aJtt1NSG6/ahORfrCIhL6fMPvSQnc8krNu28Hoi+gc3hb7ZnGOxO1F1+5rqAGN74+KqiFzbw=="; // 한국문화정보원

    /**
     * link https://www.culture.go.kr/data/openapi/openapiView.do?id=556&gubun=A
     */
    @Test
    void 문체부기관공연정보() throws UnsupportedEncodingException, MalformedURLException {
        StringBuilder urlBuilder = new StringBuilder("http://api.kcisa.kr/API_CNV_053/request"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + MY_KEY); /*서비스키*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*세션당 요청레코드수*/
//        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*페이지수*/
//        urlBuilder.append("&" + URLEncoder.encode("keyword","UTF-8") + "=" + URLEncoder.encode("검색어", "UTF-8")); /*검색어*/

        URL url = new URL(urlBuilder.toString());
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(url.toString(),
                HttpMethod.GET,
                entity,
                Map.class);

        System.out.println("response.getBody() = " + response.getBody());
    }

    @Test
    void 한국문화정보원() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/period"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + DATA_GG_KEY_DECODED); /*Service Key*/
//        urlBuilder.append("&" + URLEncoder.encode("keyword","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /**/
        urlBuilder.append("&" + URLEncoder.encode("sortStdr","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*1:등록일, 2:공연명, 3:지역*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }
}

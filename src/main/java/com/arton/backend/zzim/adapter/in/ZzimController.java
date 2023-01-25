package com.arton.backend.zzim.adapter.in;

import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.user.domain.User;
import com.arton.backend.zzim.application.port.in.ZzimDeleteDto;
import com.arton.backend.zzim.application.port.in.ZzimUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/zzim")
@RequiredArgsConstructor
public class ZzimController {
    private final ZzimUseCase zzimService;

    /**
     * Android 에서는 DELETE와 함께 본문을 전송할 수 없음
     * Tomcat, Weblogic은 Payload가 있는 DELETE 요청을 거부
     * OpenAPI 사양에서 본문이 있는 DELETE 요청 지원을 중단
     * Google Cloud HTTPS LoadBalancer가 상태 값 400을 반환하는 DELETE 요청을 거부
     * Sahi Pro는 DELETE 요청에 제공된 Body를 무시
     * 일부 버전의 Tomcat 및 Jetty는 엔터티 본문을 무시
     * 네이버나, 토스의 경우 POST로 선택 삭제를 구현했다는 글이 있어 POST로 구현을 하도록 하겠습니다.
     */
    @PostMapping("/cancel")
    public ResponseEntity deleteFavorites(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ZzimDeleteDto deleteDto) {
        long userId = Long.parseLong(userDetails.getUsername());
        zzimService.deleteUsersFavorite(userId, deleteDto);
        return ResponseEntity.noContent().build();
    }


    /**
     * 유저를 기준으로 Artist, Performance 의 찜 목록을 호출한다.
     * @param userDetails
     * @return HashMap<String, Object>
     */
    @PostMapping("/list")
    public ResponseEntity<ResponseData<Map>> zzimList(@AuthenticationPrincipal UserDetails userDetails){
        long userId = Long.parseLong(userDetails.getUsername());
        HashMap<String,Object> zzimMap = new HashMap<>();
        zzimMap.put("performance", zzimService.performanceList(userId));
        zzimMap.put("artist", zzimService.artistList(userId));


        ResponseData response = new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                zzimMap
        );
        return ResponseEntity.ok(response);
    }
}

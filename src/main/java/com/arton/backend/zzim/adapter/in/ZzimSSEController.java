package com.arton.backend.zzim.adapter.in;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.zzim.domain.SseEmitters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/zzim-sse")
public class ZzimSSEController {

    private final SseEmitters sseEmitters;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(){
        SseEmitter sseEmitter = new SseEmitter();
        sseEmitters.add(sseEmitter);

        try {
            sseEmitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected!")
            );
        } catch (IOException e) {
           throw new RuntimeException();
        }
        return ResponseEntity.ok(sseEmitter);
    }

    // 티켓 오픈 날자 브로드캐스팅
    // 유저 정보를 가져와서 해당 유저가 좋아요 한 공연의 날자를 브로드 캐스팅?
    // 날자를 어떻게 감지하는데?
    // 컨트롤러에서 이벤트를 감지하도록 하면 매번 컨트롤러를 따로 호출해야 한다는 이야긴데
    // 말이 좀 안되는듯?
    // 배치 프로그램으로 하루에 한번 감지하다가 연결되는 즉시 브로드캐스팅하는 방식은 어때?
    // 배치는 어떤식으로 도는가 하면
    // 하루에 한번 도는데...유저가 찜한 뮤지컬의 티켓오픈 날자를 조회한다. 이렇게 하면 유저 하나당 테스크가 하나가 생성되는데 너무 무리할듯
    // 테이블에 데이터를 담고 있다가...(좀 거대한 테이블이 되겠지?) 화면을 열때 조회하는 방식으로 조정하는것(확장성을 고려하면 나쁜 방식)
    // 일단 push 알림을 구현한다고 생각하고 짜야한다.
    // 향후 확장성을 고려하면 연결되는 시점에 브로드 캐스팅을 하는것을 옳지 못함(push message 구현시 방해될듯)
    // 해당 공연이 업데이트 되는 시점에 브로드캐스팅

}

package com.arton.backend.zzim.adapter.in;

import com.arton.backend.performance.applicaiton.service.SseEmitters;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * SSE를 통해 티켓오픈날자 중계
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/zzim-sse")
public class ZzimSSEController {

    private final SseEmitters sseEmitters;

    @Operation(summary = "SSE 연결", description = "SSE를 이용하여 zzim 이벤트 수신 대기")
    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(){
        SseEmitter sseEmitter = new SseEmitter();
        sseEmitters.add(sseEmitter);

        try {
            sseEmitter.send(
                SseEmitter.event()
                    .name("connect")
                    .data("connected!")
            );
        } catch (IOException e) {
           throw new RuntimeException();
        }
        return ResponseEntity.ok(sseEmitter);
    }

}

package com.arton.backend.zzim.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
public class SseEmitters {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();// Thread Safety 자료구조 사용

    public SseEmitter add(SseEmitter emitter){
        this.emitters.add(emitter);

        emitter.onCompletion(() -> {
            this.emitters.remove(emitter);
        });

        emitter.onTimeout(()->{
            emitter.complete();
        });

        return emitter;
    }
}

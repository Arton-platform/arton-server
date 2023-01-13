package com.arton.backend.faq.adapter.in.web;

import com.arton.backend.faq.application.port.in.FAQUseCase;
import com.arton.backend.faq.domain.FAQ;
import com.arton.backend.infra.shared.common.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/faq")
@RequiredArgsConstructor
@Slf4j
public class FAQController {
    // port.in UseCase 를 바라보고 사용한다.

    private final FAQUseCase faqUseCase;

    @GetMapping("/list")
    public ResponseEntity<ResponseData<List<FAQ>>> faqList(){
        log.info("[FAQ] {}","faqList");
        ResponseData<List<FAQ>> response = new ResponseData<>(
                "SUCCESS",
                HttpStatus.OK.value(),
                faqUseCase.faqList()
        );
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<FAQ>> faq(@PathVariable("id") Long id){
        log.info("[FAQ]: faqId : {}", id);
        ResponseData<FAQ> response = new ResponseData<>(
                "SUCCESS",
                HttpStatus.OK.value(),
                faqUseCase.getFaqById(id)
        );
        return ResponseEntity.ok().body(response);
    }

}

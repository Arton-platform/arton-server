package com.arton.backend.board.adapter.in;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
@Slf4j
@AllArgsConstructor
public class BoardController {

//    BoardService boardService;
//
//    @GetMapping("/faq")
//    public ResponseEntity<List<FAQEntity>> faqList(){
//        log.info("[FAQ] : faqList");
//        return ResponseEntity.ok(boardService.faqList());
//    }
}

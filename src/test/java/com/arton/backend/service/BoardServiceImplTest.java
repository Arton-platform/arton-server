package com.arton.backend.service;

import com.arton.backend.repository.board.FAQRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class BoardServiceImplTest {

    @Autowired
    BoardServiceImpl boardService;

    @Test
    void faqList() {
        assertThat(boardService.faqList().size() == 0).isTrue();
    }

}
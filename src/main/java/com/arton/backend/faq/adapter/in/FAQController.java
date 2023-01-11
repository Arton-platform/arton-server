package com.arton.backend.faq.adapter.in;

import com.arton.backend.faq.adapter.out.FAQEntity;
import com.arton.backend.faq.application.port.in.FAQListUseCase;
import com.arton.backend.faq.domain.FAQ;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/faq")
public class FAQController {
    // port.in UseCase 를 바라보고 사용한다.

    FAQListUseCase faqListUseCase;

    public List<FAQ> faqList(){
        return faqListUseCase.faqList();
    }

}

package com.arton.backend.faq.adapter.in;

import com.arton.backend.faq.adapter.out.FAQEntity;
import com.arton.backend.faq.application.port.in.FAQListUseCase;
import com.arton.backend.faq.domain.FAQ;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/faq")
@RequiredArgsConstructor
public class FAQController {
    // port.in UseCase 를 바라보고 사용한다.

    private final FAQListUseCase faqListUseCase;

    @GetMapping("/list")
    public List<FAQ> faqList(){
        faqListUseCase.faqList();
        return new ArrayList<FAQ>();
    }

}

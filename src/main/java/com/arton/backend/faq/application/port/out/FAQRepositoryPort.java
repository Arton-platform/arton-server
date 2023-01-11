package com.arton.backend.faq.application.port.out;

import com.arton.backend.faq.domain.FAQ;

import java.util.List;

public interface FAQRepositoryPort {
    //상속받아 구현할 추상 메서드를 작성한다.(Repository 에서 사용할 추상 메서드)
    public List<FAQ> loadAllFAQ();
}

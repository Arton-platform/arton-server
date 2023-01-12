package com.arton.backend.faq.application.port.out;

import com.arton.backend.faq.adapter.out.FAQEntity;

import java.util.List;
import java.util.Optional;

public interface FAQRepositoryPort {
    //상속받아 구현할 추상 메서드를 작성한다.(Repository 에서 사용할 추상 메서드)
    Optional<List<FAQEntity>> loadAllFAQ();
}

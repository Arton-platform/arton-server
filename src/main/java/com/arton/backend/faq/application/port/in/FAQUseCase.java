package com.arton.backend.faq.application.port.in;

import com.arton.backend.faq.application.data.FAQListResponseDTO;
import com.arton.backend.faq.application.data.FAQResponseDTO;
import com.arton.backend.faq.domain.FAQ;

import java.util.List;

public interface FAQUseCase {
    List<FAQ> faqList();
    /**
     * 동건님 요청사항 반영 response dto
     * 20230427
     * @return
     */
    List<FAQListResponseDTO> faqListV2();
    FAQ getFaqById(long id);
    /**
     * 동건님 요청사항 반영 response dto
     * 20230427
     * @param id
     * @return
     */
    FAQResponseDTO getFaqByIdV2(long id);
}

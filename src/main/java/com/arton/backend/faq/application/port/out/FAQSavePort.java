package com.arton.backend.faq.application.port.out;

import com.arton.backend.faq.domain.FAQ;

public interface FAQSavePort {
    FAQ save(FAQ faq);
}

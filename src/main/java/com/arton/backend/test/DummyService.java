package com.arton.backend.test;

import com.arton.backend.announcement.adapter.out.persistence.AnnouncementRepository;
import com.arton.backend.faq.adapter.out.persistence.FAQRepository;
import com.arton.backend.user.adapter.out.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DummyService {
    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;
    private final FAQRepository faqRepository;
}

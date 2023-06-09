package com.arton.backend.test;

import com.arton.backend.announcement.adapter.out.persistence.AnnouncementEntity;
import com.arton.backend.announcement.adapter.out.persistence.AnnouncementRepository;
import com.arton.backend.faq.adapter.out.persistence.FAQEntity;
import com.arton.backend.faq.adapter.out.persistence.FAQRepository;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.user.adapter.out.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class DummyService {
    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;
    private final FAQRepository faqRepository;


    public void createDummyAnnouncements() {
        Random random = new Random();
        List<UserEntity> all = userRepository.findAll();
        int userSize = all.size();
        List<AnnouncementEntity> announcementEntities = new ArrayList<>();
        for (int i = 0; i<100; i++) {
            AnnouncementEntity build = AnnouncementEntity.builder().hit(random.nextInt(userSize))
                    .title("dummy announcement title" + random.nextInt(100))
                    .content("dummy announcement content" + random.nextInt(100))
                    .user(all.get(random.nextInt(userSize))).build();
            announcementEntities.add(build);
        }
        announcementRepository.saveAll(announcementEntities);
    }

    public void createDummyFAQ() {
        Random random = new Random();
        List<UserEntity> all = userRepository.findAll();
        int userSize = all.size();
        List<FAQEntity> faqEntities = new ArrayList<>();
        for (int i = 0; i<100; i++) {
            FAQEntity build = FAQEntity.builder()
                    .title("dummy faq title" + random.nextInt(100))
                    .content("dummy faq content" + random.nextInt(100))
                    .user(all.get(random.nextInt(userSize))).build();
            faqEntities.add(build);
        }
        faqRepository.saveAll(faqEntities);
    }
}

package com.arton.backend.search.adapter.out.persistence.repository;

import com.arton.backend.administer.mail.application.data.AdminMailSearchDto;
import com.arton.backend.search.adapter.out.persistence.document.UserDocument;
import com.arton.backend.search.application.port.out.UserDocumentSavePort;
import com.arton.backend.search.application.port.out.UserDocumentSearchPort;
import com.arton.backend.user.adapter.out.persistence.mapper.UserMapper;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.arton.backend.user.adapter.out.persistence.mapper.UserMapper.*;

@Repository
@RequiredArgsConstructor
public class UserSearchRepositoryAdapter implements UserDocumentSavePort, UserDocumentSearchPort {
    private final UserSearchRepository userSearchRepository;

    @Override
    public UserDocument save(User user) {
        return userSearchRepository.save(toDocumentFromDomain(user));
    }

    @Override
    public List<UserDocument> saveAll(List<User> userDocumentList) {
        List<UserDocument> documents = userDocumentList.stream().map(UserMapper::toDocumentFromDomain).collect(Collectors.toList());
        userSearchRepository.saveAll(documents);
        return documents;
    }

    @Override
    public SearchPage<UserDocument> searchUserForMailing(AdminMailSearchDto searchDto, Pageable pageable) {
        return userSearchRepository.searchUserForMailing(searchDto, pageable);
    }
}

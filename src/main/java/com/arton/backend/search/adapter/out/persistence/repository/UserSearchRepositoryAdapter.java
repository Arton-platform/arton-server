package com.arton.backend.search.adapter.out.persistence.repository;

import com.arton.backend.administer.mail.application.data.AdminMailSearchDto;
import com.arton.backend.search.adapter.out.persistence.document.UserDocument;
import com.arton.backend.search.application.port.out.UserDocumentSavePort;
import com.arton.backend.search.application.port.out.UserDocumentSearchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserSearchRepositoryAdapter implements UserDocumentSavePort, UserDocumentSearchPort {
    private final UserSearchRepository userSearchRepository;
    @Override
    public UserDocument save(UserDocument userDocument) {
        return userSearchRepository.save(userDocument);
    }

    @Override
    public List<UserDocument> saveAll(List<UserDocument> userDocumentList) {
        List<UserDocument> result = new ArrayList<>();
        Iterable<UserDocument> userDocuments = userSearchRepository.saveAll(userDocumentList);
        userDocuments.forEach(result::add);
        return result;
    }

    @Override
    public SearchPage<UserDocument> searchUserForMailing(AdminMailSearchDto searchDto, Pageable pageable) {
        return userSearchRepository.searchUserForMailing(searchDto, pageable);
    }
}

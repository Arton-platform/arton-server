package com.arton.backend.search.application.service;

import com.arton.backend.search.adapter.out.persistence.document.UserDocument;
import com.arton.backend.search.application.port.in.UserSearchUseCase;
import com.arton.backend.search.application.port.out.UserDocumentSavePort;
import com.arton.backend.user.adapter.out.persistence.mapper.UserMapper;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class UserSearchService implements UserSearchUseCase {

    private final UserDocumentSavePort userDocumentSavePort;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void saveAll() {
        List<UserDocument> users = userRepositoryPort.findAll().stream().map(UserMapper::toDocumentFromDomain).collect(Collectors.toList());
        userDocumentSavePort.saveAll(users);
    }
}

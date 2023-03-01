package com.arton.backend.search.application.service;

import com.arton.backend.search.application.port.in.UserSearchUseCase;
import com.arton.backend.search.application.port.out.UserDocumentSavePort;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class UserSearchService implements UserSearchUseCase {

    private final UserDocumentSavePort userDocumentSavePort;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void saveAll() {
        List<User> users = userRepositoryPort.findAll();
        userDocumentSavePort.saveAll(users);
    }
}

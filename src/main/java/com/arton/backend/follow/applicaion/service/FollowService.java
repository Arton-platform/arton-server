package com.arton.backend.follow.applicaion.service;

import com.arton.backend.follow.applicaion.port.in.FollowUseCase;
import com.arton.backend.follow.applicaion.port.out.FollowRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FollowService implements FollowUseCase {
    private final FollowRepositoryPort followRepository;
}

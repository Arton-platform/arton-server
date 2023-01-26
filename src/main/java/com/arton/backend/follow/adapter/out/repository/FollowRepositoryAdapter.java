package com.arton.backend.follow.adapter.out.repository;

import com.arton.backend.follow.applicaion.port.out.FollowRepositoryPort;
import com.arton.backend.follow.domain.Follow;
import com.arton.backend.user.adapter.out.repository.UserMapper;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.follow.adapter.out.repository.FollowMapper.toDomain;
import static com.arton.backend.follow.adapter.out.repository.FollowMapper.toEntity;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FollowRepositoryAdapter implements FollowRepositoryPort {
    private final FollowRepository followRepository;

    /**
     * toUser 즉 팔로잉의 대상이 되는 유저
     * @param userId
     * @return
     */
    @Override
    public Long getFollowersCount(Long userId) {
        return followRepository.countByToUser(userId);
    }

    /**
     * fromUser 즉 팔로잉을 하는 유저
     * @param userId
     * @return
     */
    @Override
    public Long getFollowingsCount(Long userId) {
        return followRepository.countByFromUser(userId);
    }

   /**
     * 팔로우 팔로잉 관계 추가
     * @param follow
     * @return
     */
    @Override
    public Follow add(Follow follow) {
        return toDomain(followRepository.save(toEntity(follow)));
    }

    /**
     * 팔로우 관계 삭제
     * @param follow
     */
    @Override
    public void delete(Follow follow) {
        followRepository.delete(toEntity(follow));
    }

    /**
     * 자신의 팔로잉 리스트를 구한다.
     * @param userId
     * @return
     */
    @Override
    public List<User> getFollowingList(Long userId) {
        return Optional.ofNullable(followRepository.findAllByFromUser(userId))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * 자신의 팔로잉 리스트를 최신순으로 구한다.
     * @param userId
     * @return
     */
    @Override
    public List<User> getLatestFollowingList(Long userId) {
        return Optional.ofNullable(followRepository.findAllByFromUserLatest(userId))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * 자신의 팔로워 리스트를 구한다.
     * @param userId
     * @return
     */
    @Override
    public List<User> getFollowerList(Long userId) {
        return Optional.ofNullable(followRepository.findAllByToUser(userId))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * 자신의 팔로워 리스트를 최신순으로 구한다.
     * @param userId
     * @return
     */
    @Override
    public List<User> getLatestFollowerList(Long userId) {
        return Optional.ofNullable(followRepository.findAllByToUserLatest(userId))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }


}

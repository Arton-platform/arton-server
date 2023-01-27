package com.arton.backend.follow.applicaion.service;

import com.arton.backend.follow.applicaion.port.in.FollowUseCase;
import com.arton.backend.follow.applicaion.port.in.UserFollowDto;
import com.arton.backend.follow.applicaion.port.in.UserFollowSearchDto;
import com.arton.backend.follow.applicaion.port.in.UserShortDto;
import com.arton.backend.follow.applicaion.port.out.FollowRepositoryPort;
import com.arton.backend.follow.domain.Follow;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FollowService implements FollowUseCase {
    private final FollowRepositoryPort followRepository;
    private final UserRepositoryPort userRepository;

    @Override
    public UserFollowDto getFollowers(Long userId, UserFollowSearchDto userFollowSearchDto) {
        findUser(userId);
        // 유저의 팔로워수
        Long followersCount = followRepository.getFollowersCount(userId);
        // 유저의 팔로잉수
        Long followingsCount = followRepository.getFollowingsCount(userId);
        // 팔로워 short response
        List<User> followerList = followRepository.getFollowerList(userId, userFollowSearchDto);
        List<UserShortDto> shortResponse = followerList.stream().map(UserShortDto::to).collect(Collectors.toList());
        return UserFollowDto.builder().id(userId).followers(followersCount).followings(followingsCount).users(shortResponse).build();
    }

    @Override
    public UserFollowDto getFollowings(Long userId, UserFollowSearchDto userFollowSearchDto) {
        findUser(userId);
        // 유저의 팔로워수
        Long followersCount = followRepository.getFollowersCount(userId);
        // 유저의 팔로잉수
        Long followingsCount = followRepository.getFollowingsCount(userId);
        // 팔로워 short response
        List<User> followerList = followRepository.getFollowingList(userId, userFollowSearchDto);
        List<UserShortDto> shortResponse = followerList.stream().map(UserShortDto::to).collect(Collectors.toList());
        return UserFollowDto.builder().id(userId).followers(followersCount).followings(followingsCount).users(shortResponse).build();
    }

    /**
     * toUser로부터 Id가 fromUser인 팔로워를 제거한다.
     * @param toUser
     * @param fromUser
     */
    @Override
    public Long removeFollower(Long toUser, Long fromUser) {
        // 팔로잉 대상
        User to = findUser(toUser);
        // 팔로워
        User from = findUser(fromUser);
        // 관계
        Follow follow = Follow.builder().toUser(to.getId()).fromUser(from.getId()).build();
        // 제거
        followRepository.delete(follow);
        // 제거한 회원 번호 return
        return fromUser;
    }

    /**
     * fromUser가 toUser 팔로잉을 취소한다.
     * @param fromUser
     * @param toUser
     */
    @Override
    public Long unfollow(Long fromUser, Long toUser) {
        // 팔로워
        User from = findUser(fromUser);
        // 팔로잉 대상
        User to = findUser(toUser);
        // 관계
        Follow follow = Follow.builder().fromUser(from.getId()).toUser(to.getId()).build();
        // 제거
        followRepository.delete(follow);
        // 팔로잉 취소한 회원 번호 return
        return toUser;
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
    }
}
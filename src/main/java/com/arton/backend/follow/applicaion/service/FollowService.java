package com.arton.backend.follow.applicaion.service;

import com.arton.backend.follow.applicaion.port.in.*;
import com.arton.backend.follow.applicaion.port.out.FollowRegistRepositoryPort;
import com.arton.backend.follow.applicaion.port.out.FollowRepositoryPort;
import com.arton.backend.follow.applicaion.port.out.UnFollowRepositoryPort;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FollowService implements FollowUseCase, UnFollowUseCase, FollowRegisterUseCase {
    private final FollowRepositoryPort followRepository;
    private final FollowRegistRepositoryPort followRegistRepository;
    private final UnFollowRepositoryPort unFollowRepository;
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
        unFollowRepository.delete(follow);
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
        unFollowRepository.delete(follow);
        // 팔로잉 취소한 회원 번호 return
        return toUser;
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public Follow followUser(Long fromUser, PostFollowDto postFollowDto) {
        User from = findUser(fromUser);
        if (postFollowDto == null) {
            throw new CustomException(ErrorCode.PARAMETER_NOT_VALID.getMessage(), ErrorCode.PARAMETER_NOT_VALID);
        }
        Long toUser = Optional.ofNullable(postFollowDto.getUser()).orElseThrow(() -> new CustomException(ErrorCode.PARAMETER_NOT_VALID.getMessage(), ErrorCode.PARAMETER_NOT_VALID));
        if (from.getId().equals(toUser)) { // 본인이 본인을 팔로우 하는 잘못된 요청.
            throw new CustomException(ErrorCode.FOLLOWING_INVALID.getMessage(), ErrorCode.FOLLOWING_INVALID);
        }
        User to = findUser(toUser);
        Follow follow = Follow.builder()
                .fromUser(from.getId())
                .toUser(to.getId())
                .build();
        boolean exist = followRepository.isExist(follow);
        if (exist) {
            throw new CustomException(ErrorCode.FOLLOWING_IS_EXIST.getMessage(), ErrorCode.FOLLOWING_IS_EXIST);
        }
        followRegistRepository.add(follow);
        return follow;
    }
}

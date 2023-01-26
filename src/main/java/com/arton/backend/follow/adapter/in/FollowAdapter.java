package com.arton.backend.follow.adapter.in;

import com.arton.backend.follow.applicaion.port.in.FollowUseCase;
import com.arton.backend.follow.applicaion.port.in.UserFollowDto;
import com.arton.backend.follow.applicaion.port.in.UserFollowSearchDto;
import com.arton.backend.infra.shared.common.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowAdapter {
    private final FollowUseCase followService;

    /**
     * query parameter를 받아 팔로워를 리턴한다.
     * @param userDetails
     * @param userFollowSearchDto
     * @return
     */
    @GetMapping("/user/my/follower")
    public ResponseData<UserFollowDto> getFollower(@AuthenticationPrincipal UserDetails userDetails, UserFollowSearchDto userFollowSearchDto) {
        long userId = Long.parseLong(userDetails.getUsername());
        return new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                followService.getFollowers(userId, userFollowSearchDto));
    }

    /**
     * query parameter를 받아 팔로잉을 리턴한다.
     * @param userDetails
     * @param userFollowSearchDto
     * @return
     */
    @GetMapping("/user/my/following")
    public ResponseData<UserFollowDto> getFollowing(@AuthenticationPrincipal UserDetails userDetails, UserFollowSearchDto userFollowSearchDto) {
        long userId = Long.parseLong(userDetails.getUsername());
        return new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                followService.getFollowings(userId, userFollowSearchDto));
    }
}

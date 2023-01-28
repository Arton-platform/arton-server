package com.arton.backend.follow.adapter.in;

import com.arton.backend.follow.applicaion.port.in.FollowUseCase;
import com.arton.backend.follow.applicaion.port.in.UnFollowUseCase;
import com.arton.backend.follow.applicaion.port.in.UserFollowDto;
import com.arton.backend.follow.applicaion.port.in.UserFollowSearchDto;
import com.arton.backend.infra.shared.common.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {
    private final FollowUseCase followService;
    private final UnFollowUseCase unFollowService;

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

    /**
     * 팔로워 ID를 입력받아 해당 팔로워를 제거한다.
     * @param userDetails
     * @param id
     * @return
     */
    @DeleteMapping("/user/my/follower/{id}")
    public ResponseData<Long> removeFollower(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "id", required = true) Long id) {
        long userId = Long.parseLong(userDetails.getUsername());
        return new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                unFollowService.removeFollower(userId, id)
        );
    }

    /**
     * 팔로잉 ID를 입력받아 해당 팔로잉을 제거한다.
     * @param userDetails
     * @param id
     * @return
     */
    @DeleteMapping("/user/my/following/{id}")
    public ResponseData<Long> removeFollowing(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "id", required = true) Long id) {
        long userId = Long.parseLong(userDetails.getUsername());
        return new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                unFollowService.unfollow(userId, id)
        );
    }
}

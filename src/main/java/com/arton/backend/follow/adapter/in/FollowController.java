package com.arton.backend.follow.adapter.in;

import com.arton.backend.follow.applicaion.port.in.*;
import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FollowController {
    private final FollowUseCase followService;
    private final UnFollowUseCase unFollowService;
    private final FollowRegisterUseCase followRegisterService;

    /**
     * query parameter를 받아 팔로워를 리턴한다.
     * @param userDetails
     * @param userFollowSearchDto
     * @return
     */
    @GetMapping("/user/my/follower")
    public ResponseEntity<ResponseData<UserFollowDto>> getFollower(@AuthenticationPrincipal UserDetails userDetails, UserFollowSearchDto userFollowSearchDto) {
        long userId = Long.parseLong(userDetails.getUsername());
        ResponseData response = new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                followService.getFollowers(userId, userFollowSearchDto));
        return ResponseEntity.ok(response);
    }

    /**
     * query parameter를 받아 팔로잉을 리턴한다.
     * @param userDetails
     * @param userFollowSearchDto
     * @return
     */
    @GetMapping("/user/my/following")
    public ResponseEntity<ResponseData<UserFollowDto>> getFollowing(@AuthenticationPrincipal UserDetails userDetails, UserFollowSearchDto userFollowSearchDto) {
        long userId = Long.parseLong(userDetails.getUsername());
        ResponseData response = new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                followService.getFollowings(userId, userFollowSearchDto));
        return ResponseEntity.ok(response);
    }

    /**
     * 팔로워 ID를 입력받아 해당 팔로워를 제거한다.
     * @param userDetails
     * @param id
     * @return
     */
    @DeleteMapping("/user/my/follower/{id}")
    public ResponseEntity<ResponseData<Long>> removeFollower(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "id", required = true) Long id) {
        long userId = Long.parseLong(userDetails.getUsername());
        ResponseData response = new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                unFollowService.removeFollower(userId, id)
        );
        return ResponseEntity.ok(response);
    }

    /**
     * 팔로잉 ID를 입력받아 해당 팔로잉을 제거한다.
     * @param userDetails
     * @param id
     * @return
     */
    @DeleteMapping("/user/my/following/{id}")
    public ResponseEntity<ResponseData<Long>> removeFollowing(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "id", required = true) Long id) {
        long userId = Long.parseLong(userDetails.getUsername());
        ResponseData response = new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                unFollowService.unfollow(userId, id)
        );
        return ResponseEntity.ok(response);
    }

    /**
     * 팔로잉하기
     * @param userDetails
     * @param postFollowDto
     * @return
     */
    @PostMapping("/follow")
    public ResponseEntity<CommonResponse> followingUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody PostFollowDto postFollowDto) {
        long userId = Long.parseLong(userDetails.getUsername());
        followRegisterService.followUser(userId, postFollowDto);
        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("SUCCESS").build();
        return ResponseEntity.ok(response);
    }
}

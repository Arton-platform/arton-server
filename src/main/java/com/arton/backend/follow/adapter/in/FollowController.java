package com.arton.backend.follow.adapter.in;

import com.arton.backend.follow.applicaion.data.PostFollowDto;
import com.arton.backend.follow.applicaion.data.UserFollowDto;
import com.arton.backend.follow.applicaion.data.UserFollowSearchDto;
import com.arton.backend.follow.applicaion.port.in.*;
import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.infra.shared.exception.ErrorResponse;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "유저 팔로워 보기", description = "유저의 팔로워 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로워 정보 가져오기 성공",
                    content = @Content( schema = @Schema(implementation = UserFollowDto.class))),
            @ApiResponse(responseCode = "404", description = "유저 찾을 수 없음.",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class)))})
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
    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "유저 팔로잉 보기", description = "유저의 팔로잉 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로잉 정보 가져오기 성공",
                    content = @Content( schema = @Schema(implementation = UserFollowDto.class))),
            @ApiResponse(responseCode = "404", description = "유저 찾을 수 없음.",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class)))})
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
    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "유저 팔로워 제거하기", description = "유저의 팔로워를 제거합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로워 제거 성공",
                    content = @Content( schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "404", description = "유저 찾을 수 없음.",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class)))})
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
    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "유저 팔로잉 제거하기", description = "유저의 팔로잉을 제거합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로우 해제 성공",
                    content = @Content( schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "404", description = "유저 찾을 수 없음.",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class)))})
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
    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "유저 팔로잉", description = "특정 유저를 팔로우합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로우 성공",
                    content = @Content( schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "유저 찾을 수 없음.",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/follow")
    public ResponseEntity<CommonResponse> followingUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody PostFollowDto postFollowDto) {
        long userId = Long.parseLong(userDetails.getUsername());
        followRegisterService.followUser(userId, postFollowDto);
        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message("SUCCESS").build();
        return ResponseEntity.ok(response);
    }

    /**
     * query parameter를 받아 팔로워를 리턴한다.
     * @param userDetails
     * @param userFollowSearchDto
     * @return
     */
    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "유저의 팔로워 보기", description = "특정 유저의 팔로워 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로워 정보 반환 성공",
                    content = @Content( schema = @Schema(implementation = UserFollowDto.class))),
            @ApiResponse(responseCode = "404", description = "유저 찾을 수 없음.",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/user/{id}/follower")
    public ResponseEntity<ResponseData<UserFollowDto>> getFollower(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "id", required = true) Long id, UserFollowSearchDto userFollowSearchDto) {
        ResponseData response = new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                followService.getFollowers(id, userFollowSearchDto));
        return ResponseEntity.ok(response);
    }

    /**
     * query parameter를 받아 팔로잉을 리턴한다.
     * @param userDetails
     * @param userFollowSearchDto
     * @return
     */
    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "유저의 팔로잉 보기", description = "특정 유저의 팔로잉 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로잉 정보 반환 성공",
                    content = @Content( schema = @Schema(implementation = UserFollowDto.class))),
            @ApiResponse(responseCode = "404", description = "유저 찾을 수 없음.",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/user/{id}/following")
    public ResponseEntity<ResponseData<UserFollowDto>> getFollowing(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "id", required = true) Long id, UserFollowSearchDto userFollowSearchDto) {
        ResponseData response = new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                followService.getFollowings(id, userFollowSearchDto));
        return ResponseEntity.ok(response);
    }

}

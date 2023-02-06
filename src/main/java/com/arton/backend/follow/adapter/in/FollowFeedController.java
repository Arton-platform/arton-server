package com.arton.backend.follow.adapter.in;

import com.arton.backend.follow.applicaion.port.in.FollowFeedUseCase;
import com.arton.backend.follow.domain.FollowFeed;
import com.arton.backend.infra.shared.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow-feed")
public class FollowFeedController {
    private final FollowFeedUseCase followFeedUseCase;

    /**
     * @param followFeed
     * @Desc 나를 팔로우 할때
     */
    @Operation(
            summary = "나를 팔로우 할때 피드에 저장",
            description = "다른사람이 나를 팔로우 할때"
    )
    @PostMapping("/follow-me")
    public ResponseEntity<CommonResponse> followMe(@RequestBody FollowFeed followFeed){
        followFeedUseCase.followMe(followFeed);

        CommonResponse response = CommonResponse.builder()
                .message("SUCCESS")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(response);
    }

    /**
     * @param followFeed
     * @Desc 팔로우 한사람이 댓글을 남겼을때
     */
    @Operation(
            summary = "팔로우 코멘트",
            description = "내가 팔로우 한사람이 댓글을 달았을때"
    )
    @PostMapping("/comment-from-follow")
    public ResponseEntity<CommonResponse> commentFromFollow(@RequestBody FollowFeed followFeed){
        followFeedUseCase.commentFromFollow(followFeed);

        CommonResponse response = CommonResponse.builder()
                .message("SUCCESS")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(response);
    }

    /**
     * @param followFeed
     * @Desc 팔로우 한사람이 댓글을 남겼을때
     */
    @Operation(
            summary = "팔로우 리뷰",
            description = "팔로우 한사람이 리뷰를 달았을때"
    )
    @PostMapping("/review-from-follow")
    public ResponseEntity<CommonResponse> reviewFromFollow(@RequestBody FollowFeed followFeed){
        followFeedUseCase.reviewFromFollow(followFeed);

        CommonResponse response = CommonResponse.builder()
                .message("SUCCESS")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(response);
    }

    /**
     * @param followFeed
     * @Desc 내 리뷰에 졸아요가 눌렸을때
     */
    @Operation(
            summary = "리뷰 좋아요",
            description = "내가 들록한 리뷰 좋아요를 눌렀을때"
    )
    @PostMapping("/my-review-like")
    public ResponseEntity<CommonResponse> myReviewLike(@RequestBody FollowFeed followFeed){
        followFeedUseCase.myReviewLike(followFeed);

        CommonResponse response = CommonResponse.builder()
                .message("SUCCESS")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(response);
    }

    /**
     * @param followFeed
     * @Desc 내 리뷰에 댓글이 등록됐을때
     */
    @Operation(
            summary = "내 리뷰 댓글",
            description = "내가 등록한 리뷰에 댓글이 달렸을때"
    )
    @PostMapping("/my-review-commented")
    public ResponseEntity<CommonResponse> myReviewCommented(@RequestBody FollowFeed followFeed){
        followFeedUseCase.myReviewCommented(followFeed);

        CommonResponse response = CommonResponse.builder()
                .message("SUCCESS")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(response);
    }
}

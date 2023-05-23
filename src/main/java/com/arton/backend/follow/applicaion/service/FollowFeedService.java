package com.arton.backend.follow.applicaion.service;

import com.arton.backend.comment.application.port.out.CommentFindPort;
import com.arton.backend.comment.application.port.out.CommentRegistPort;
import com.arton.backend.comment.domain.Comment;
import com.arton.backend.follow.adapter.out.persistence.entity.FollowFeedEntity;
import com.arton.backend.follow.applicaion.data.FollowFeedMapper;
import com.arton.backend.follow.applicaion.data.FollowFeedRequestDto;
import com.arton.backend.follow.applicaion.port.out.FollowFeedPort;
import com.arton.backend.follow.applicaion.port.in.FollowFeedUseCase;
import com.arton.backend.follow.domain.FeedType;
import com.arton.backend.follow.domain.FollowFeed;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.review.application.port.out.ReviewFindPort;
import com.arton.backend.review.domain.Review;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class FollowFeedService implements FollowFeedUseCase {

    private final FollowFeedPort followFeedPort;
    private final UserRepositoryPort userRepositoryPort;
    private final CommentFindPort commentFindPort;
    private final ReviewFindPort reviewFindPort;
    private final FollowFeedMapper followFeedMapper;

    /**
     * @param followFeed
     * @Desc 내 리뷰에 좋아요를 눌렸을때
     */
    @Override
    public void myReviewLike(FollowFeedRequestDto followFeed){
        User fromUser = userRepositoryPort.findById(followFeed.getFromUserId()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        User toUser = userRepositoryPort.findById(followFeed.getToUserId()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        Review review = reviewFindPort.findById(followFeed.getReviewId()).orElseThrow(() -> new CustomException(ErrorCode.SELECT_ERROR.getMessage(), ErrorCode.SELECT_ERROR));
        Comment comment = commentFindPort.findById(followFeed.getCommentId()).orElseThrow(() -> new CustomException(ErrorCode.SELECT_ERROR.getMessage(), ErrorCode.SELECT_ERROR));
        String feedContent = fromUser.getNickname() + " " + FeedType.LIKE_MY_REVIEW;
        FollowFeed feed = FollowFeed.builder().review(review)
                .comment(comment)
                .fromUser(fromUser)
                .toUser(toUser)
                .build();
        FollowFeedEntity entity = followFeedMapper.toEntity(feed, feedContent);
        followFeedPort.save(entity);
        //TODO:
        // Alert User
    }

    /**
     * @Desc 내 리뷰에 댓글이 달렸을때
     */
    @Override
    public void myReviewCommented(FollowFeedRequestDto followFeed){
        User fromUser = userRepositoryPort.findById(followFeed.getFromUserId()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        User toUser = userRepositoryPort.findById(followFeed.getToUserId()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        Review review = reviewFindPort.findById(followFeed.getReviewId()).orElseThrow(() -> new CustomException(ErrorCode.SELECT_ERROR.getMessage(), ErrorCode.SELECT_ERROR));
        Comment comment = commentFindPort.findById(followFeed.getCommentId()).orElseThrow(() -> new CustomException(ErrorCode.SELECT_ERROR.getMessage(), ErrorCode.SELECT_ERROR));
        String feedContent = fromUser.getNickname() + " " + FeedType.COMMENT_MY_REVIEW;
        FollowFeed feed = FollowFeed.builder().review(review)
                .comment(comment)
                .fromUser(fromUser)
                .toUser(toUser)
                .build();
        FollowFeedEntity entity = followFeedMapper.toEntity(feed, feedContent);
        followFeedPort.save(entity);
        //TODO:
        // Alert User
    }

    /**
    * @Desc 팔로우 한사람이 리뷰를 남겼을때
    */
    @Override
    public void reviewFromFollow(FollowFeedRequestDto followFeed){
        User fromUser = userRepositoryPort.findById(followFeed.getFromUserId()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        User toUser = userRepositoryPort.findById(followFeed.getToUserId()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        Review review = reviewFindPort.findById(followFeed.getReviewId()).orElseThrow(() -> new CustomException(ErrorCode.SELECT_ERROR.getMessage(), ErrorCode.SELECT_ERROR));
        Comment comment = commentFindPort.findById(followFeed.getCommentId()).orElseThrow(() -> new CustomException(ErrorCode.SELECT_ERROR.getMessage(), ErrorCode.SELECT_ERROR));
        String feedContent = fromUser.getNickname() + " " +  FeedType.FOLLOW_REVIEW_REGIST;
        FollowFeed feed = FollowFeed.builder().review(review)
                .comment(comment)
                .fromUser(fromUser)
                .toUser(toUser)
                .build();
        FollowFeedEntity entity = followFeedMapper.toEntity(feed, feedContent);
        followFeedPort.save(entity);
        //TODO:
        // Alert User
    }

    /**
    * @Desc 팔로우 한사람이 댓글을 남겼을때
    */
    @Override
    public void commentFromFollow(FollowFeedRequestDto followFeed){
        User fromUser = userRepositoryPort.findById(followFeed.getFromUserId()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        User toUser = userRepositoryPort.findById(followFeed.getToUserId()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        Review review = reviewFindPort.findById(followFeed.getReviewId()).orElseThrow(() -> new CustomException(ErrorCode.SELECT_ERROR.getMessage(), ErrorCode.SELECT_ERROR));
        Comment comment = commentFindPort.findById(followFeed.getCommentId()).orElseThrow(() -> new CustomException(ErrorCode.SELECT_ERROR.getMessage(), ErrorCode.SELECT_ERROR));
        String feedContent = fromUser.getNickname() + " " + FeedType.FOLLOW_COMMENT_REGIST;
        FollowFeed feed = FollowFeed.builder().review(review)
                .comment(comment)
                .fromUser(fromUser)
                .toUser(toUser)
                .build();
        FollowFeedEntity entity = followFeedMapper.toEntity(feed, feedContent);
        followFeedPort.save(entity);
        //TODO:
        // Alert User
    }

    /**
    * @Desc 다른사람이 나를 팔로우 했을때
    */
//    @Override
//    public void followMe(FollowFeed followFeed){
//        String feedContent = followFeed.getFromUser() + " " + FeedType.FOLLOW_ME;
//        FollowFeedEntity entity = followFeedMapper.toEntity(followFeed, feedContent);
//        followFeedPort.save(entity);
//        //TODO:
//        // Alert User
//    }
    @Override
    public void followMe(FollowFeedRequestDto followFeed) {
        User fromUser = userRepositoryPort.findById(followFeed.getFromUserId()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        User toUser = userRepositoryPort.findById(followFeed.getToUserId()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        Review review = reviewFindPort.findById(followFeed.getReviewId()).orElseThrow(() -> new CustomException(ErrorCode.SELECT_ERROR.getMessage(), ErrorCode.SELECT_ERROR));
        Comment comment = commentFindPort.findById(followFeed.getCommentId()).orElseThrow(() -> new CustomException(ErrorCode.SELECT_ERROR.getMessage(), ErrorCode.SELECT_ERROR));
        String feedContent = fromUser.getNickname() + " " + FeedType.FOLLOW_ME;
        FollowFeed feed = FollowFeed.builder().review(review)
                .comment(comment)
                .fromUser(fromUser)
                .toUser(toUser)
                .build();
        FollowFeedEntity entity = followFeedMapper.toEntity(feed, feedContent);
        followFeedPort.save(entity);
    }
}

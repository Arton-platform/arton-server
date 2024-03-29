package com.arton.backend.follow.adapter.out.persistence.entity;

import com.arton.backend.comment.adapter.out.persistence.CommentEntity;
import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
public class FollowFeedEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_user")
    private UserEntity fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user")
    private UserEntity toUser;

    @ManyToOne
    @JoinColumn(name = "reviewId")
    private ReviewEntity review;

    @ManyToOne
    @JoinColumn(name = "commentId")
    private CommentEntity comment;

    private String feedContent;

}

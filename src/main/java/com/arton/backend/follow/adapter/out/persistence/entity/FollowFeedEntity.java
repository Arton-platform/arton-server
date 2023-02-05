package com.arton.backend.follow.adapter.out.persistence.entity;

import com.arton.backend.infra.shared.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Getter
@Builder
public class FollowFeedEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long fromUser;

    private Long toUser;

    private Long reviewId;

    private Long commentId;

    private String feedContent;

}

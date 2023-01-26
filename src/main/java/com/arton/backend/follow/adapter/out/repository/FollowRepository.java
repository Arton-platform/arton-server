package com.arton.backend.follow.adapter.out.repository;

import com.arton.backend.user.adapter.out.repository.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<FollowEntity, FollowEntity.PK> {
    /**
     * 자신을 따르는 팔로워의 수
     * @param toUser
     * @return
     */
    Long countByToUser(Long toUser);

    /**
     * 자신이 팔로잉 하는 사람의 수
     * @param fromUser
     * @return
     */
    Long countByFromUser(Long fromUser);

    /**
     * 유저가 팔로잉 하는 유저 리스트를 반환한다.
     * @param userId
     * @return
     */
    @Query(value = "select u from FollowEntity f inner join UserEntity u on f.toUser = u.id where f.fromUser = :userId")
    List<UserEntity> findAllByFromUser(@Param("userId") Long userId);
    /**
     * 유저가 팔로잉 하는 유저 리스트를 최신순으로 반환한다.
     * @param userId
     * @return
     */
    @Query(value = "select u from FollowEntity f inner join UserEntity u on f.toUser = u.id where f.fromUser = :userId order by f.createdDate desc")
    List<UserEntity> findAllByFromUserLatest(@Param("userId") Long userId);


    /**
     * 자신을 팔로잉 하는 팔로워 리스트를 반환한다.
     * @param userId
     * @return
     */
    @Query(value = "select u from FollowEntity f inner join UserEntity u on f.fromUser = u.id where f.toUser = :userId")
    List<UserEntity> findAllByToUser(@Param("userId") Long userId);

    /**
     * 자신을 팔로잉 하는 팔로워 리스트를 최신순으로 반환한다.
     * @param userId
     * @return
     */
    @Query(value = "select u from FollowEntity f inner join UserEntity u on f.fromUser = u.id where f.toUser = :userId order by f.createdDate desc")
    List<UserEntity> findAllByToUserLatest(@Param("userId") Long userId);
}

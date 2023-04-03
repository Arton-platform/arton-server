package com.arton.backend.image.adapter.out.persistence.entity;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "User_Image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"user"})
public class UserImageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_image_id")
    private Long id;
    private String imageUrl;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Builder
    public UserImageEntity(LocalDateTime createdDate, LocalDateTime updatedDate, Long id, String imageUrl, UserEntity user) {
        super(createdDate, updatedDate);
        this.id = id;
        this.imageUrl = imageUrl;
        this.user = user;
    }
}

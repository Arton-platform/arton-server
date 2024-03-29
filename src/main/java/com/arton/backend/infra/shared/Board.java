package com.arton.backend.infra.shared;

import com.arton.backend.image.domain.Image;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
public class Board {
    @OneToOne
    @JoinColumn(name = "id")
    private UserEntity user;
    private int hit;
    @ManyToOne(optional = true)
    @JoinColumn(name = "imageId")
    private Image image;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime updateDate;

    public Board(UserEntity user, int hit, Image image, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.user = user;
        this.hit = hit;
        this.image = image;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    public Board() {

    }

    public Board(Board board) {
        this.user = board.getUser();
        this.hit = board.getHit();
        this.image = board.getImage();
        this.createdDate = board.getCreatedDate();
        this.updateDate = board.getUpdateDate();
    }
}

package com.arton.backend.dto.board;

import com.arton.backend.dto.image.Image;
import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.user.domain.User;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
public class Board extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "id")
    private User user;
    private int hit;
    @ManyToOne(optional = true)
    @JoinColumn(name = "imageId")
    private Image image;
}

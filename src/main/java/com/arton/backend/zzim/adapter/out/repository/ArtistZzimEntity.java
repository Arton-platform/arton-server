package com.arton.backend.zzim.adapter.out.repository;

import com.arton.backend.artist.adapter.out.repository.ArtistEntity;
import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.user.adapter.out.repository.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "Artist_Zzim")
@ToString
public class ArtistZzimEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private ArtistEntity artist;

    public void setUser(UserEntity user) {
        this.user = user;
        if (!user.getArtistZzims().contains(this)) {
            user.getArtistZzims().add(this);
        }
    }

    @Builder
    public ArtistZzimEntity(LocalDateTime createdDate, LocalDateTime updateDate, Long id, UserEntity user, ArtistEntity artist) {
        super(createdDate, updateDate);
        this.id = id;
        this.user = user;
        this.artist = artist;
    }
}

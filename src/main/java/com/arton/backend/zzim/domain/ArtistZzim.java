package com.arton.backend.zzim.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ArtistZzim{
    private Long id;
    private Long artist;
    private Long user;
    /** 가입일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updatedDate;
    public void setUser(Long user) {
        this.user = user;
    }

    @Builder
    public ArtistZzim(Long id, Long artist, Long user, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.id = id;
        this.artist = artist;
        this.user = user;
        this.createdDate = createdDate;
        this.updatedDate = updateDate;
    }
}

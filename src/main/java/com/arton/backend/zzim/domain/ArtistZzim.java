package com.arton.backend.zzim.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ArtistZzim{
    private Long id;
    private Long artistId;
    private Long userId;
    /** 가입일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updatedDate;
    public void setUser(Long userId) {
        this.userId = userId;
    }

    @Builder
    public ArtistZzim(Long id, Long artistId, Long userId, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.artistId = artistId;
        this.userId = userId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}

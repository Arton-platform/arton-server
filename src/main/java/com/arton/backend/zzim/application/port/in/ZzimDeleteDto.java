package com.arton.backend.zzim.application.port.in;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ZzimDeleteDto {
    private List<Long> artists = new ArrayList<>();
    private List<Long> performances = new ArrayList<>();

    @Builder
    public ZzimDeleteDto(List<Long> artists, List<Long> performances) {
        this.artists = artists;
        this.performances = performances;
    }
}

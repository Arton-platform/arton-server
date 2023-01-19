package com.arton.backend.zzim.application.port.in;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ZzimDeleteDto {
    private List<Long> artists = new ArrayList<>();
    private List<Long> performances = new ArrayList<>();
}

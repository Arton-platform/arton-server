package com.arton.backend.zzim.domain;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PerformanceZzimCompoundKey implements Serializable {
    private Long performance;
    private Long user;
}

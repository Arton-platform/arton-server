package com.arton.backend.zzim.adapter.out.repository;

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

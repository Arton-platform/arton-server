package com.arton.backend.administer.banner.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Banner {
    private Long id;
    private int seq;
    private String title;
    private String isActive;
}

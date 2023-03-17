package com.arton.backend.administer.popup.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PopUpDto {
    private Long id;
    private LocalDateTime starTime;
    private LocalDateTime endTime;
    private boolean isActive;
    private int positionX;
    private int positionY;
    private int size;
    private String url;
    private String content;
}

package com.arton.backend.administer.popup.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.arton.backend.infra.shared.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PopUpEntity extends Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime starTime;
    private LocalDateTime endTime;
    private boolean isActive;
    private int positionX;
    private int positionY;
    private int size;
    private String url;
    private String content;

    public void update(Long id, LocalDateTime starTime, LocalDateTime endTime, boolean isActive, int positionX, int positionY, int size, String url, String content){
        this.id = id;
        this.starTime = starTime;
        this.endTime = endTime;
        this.isActive = isActive;
        this.positionX = positionX;
        this.positionY = positionY;
        this.size = size;
        this.url = url;
        this.content = content;
    }

}

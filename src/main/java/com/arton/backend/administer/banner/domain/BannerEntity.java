package com.arton.backend.administer.banner.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.arton.backend.infra.shared.Board;

import lombok.Getter;


@Entity
@Getter
public class BannerEntity extends Board{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int seq;
    private String title;
    private String isActive;

    public BannerEntity(){}

    public BannerEntity(Board board, Long id, int seq, String title, String isActive){
        super(board);
        this.id = id;
        this.seq = seq;
        this.title = title;
        this.isActive = isActive;
    }

    public void update(int seq, String title, String isActive){
        this.seq = seq;
        this.title = title;
        this.isActive = isActive;
    }
}

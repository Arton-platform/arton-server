package com.arton.backend.administer.banner.domain;

import com.arton.backend.infra.shared.Board;

import lombok.Getter;

@Getter
public class Banner extends Board{
    private Long id;
    private int seq;
    private String title;
    private String isActive;

    public Banner(Board board, Long id, int seq, String title, String isActive){
        super(board);
        this.id = id;
        this.seq = seq;
        this.title = title;
        this.isActive = isActive;
    }
}

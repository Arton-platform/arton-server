package com.arton.backend.entity.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Announcement extends Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long announcementId;
    private String title;
    private String content;
}

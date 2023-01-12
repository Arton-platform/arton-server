package com.arton.backend.board.adapter.out.persistence.review;

import com.arton.backend.board.adapter.out.persistence.Board;
import com.arton.backend.performance.domain.Performance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Review extends Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    @ManyToOne
    @JoinColumn(name = "performanceId")
    private Performance performance;
    private String content;
    private float starScore;

}

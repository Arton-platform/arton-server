package com.arton.backend.repository.board;

import com.arton.backend.entity.board.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}

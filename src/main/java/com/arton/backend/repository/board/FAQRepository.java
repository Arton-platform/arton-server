package com.arton.backend.repository.board;

import com.arton.backend.entity.board.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
}

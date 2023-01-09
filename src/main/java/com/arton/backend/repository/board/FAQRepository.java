package com.arton.backend.repository.board;

import com.arton.backend.entity.board.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FAQRepository extends JpaRepository<FAQ, Long> {

    Optional<List<FAQ>> findAllByOrderByCreatedDateDesc();
}

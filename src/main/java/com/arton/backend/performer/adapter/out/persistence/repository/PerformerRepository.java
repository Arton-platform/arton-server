package com.arton.backend.performer.adapter.out.persistence.repository;

import com.arton.backend.performer.adapter.out.persistence.entity.PerformerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformerRepository extends JpaRepository<PerformerEntity, Long> {

}

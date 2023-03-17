package com.arton.backend.administer.popup.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arton.backend.administer.popup.domain.PopUpEntity;

public interface PopUpRepository extends JpaRepository<PopUpEntity,Long>{

}

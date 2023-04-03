package com.arton.backend.administer.popup.application.out;

import java.util.List;

import com.arton.backend.administer.popup.domain.PopUpEntity;

public interface PopUpSelectAllPort {

    List<PopUpEntity> findAll();

}

package com.arton.backend.administer.popup.application.in;

import java.util.List;

import com.arton.backend.administer.popup.domain.PopUpDto;

public interface PopUpSelectAllUseCase {
    List<PopUpDto> selectAll();
}

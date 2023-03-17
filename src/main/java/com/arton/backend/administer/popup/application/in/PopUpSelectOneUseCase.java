package com.arton.backend.administer.popup.application.in;

import com.arton.backend.administer.popup.domain.PopUpDto;

public interface PopUpSelectOneUseCase {
    PopUpDto selectOne(Long id);
}

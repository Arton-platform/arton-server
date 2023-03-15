package com.arton.backend.administer.popup.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.arton.backend.administer.popup.application.in.PopUpDeleteUseCase;
import com.arton.backend.administer.popup.application.in.PopUpRegistUseCase;
import com.arton.backend.administer.popup.application.in.PopUpSelectAllUseCase;
import com.arton.backend.administer.popup.application.in.PopUpSelectOneUseCase;
import com.arton.backend.administer.popup.application.in.PopUpUpdateUseCase;
import com.arton.backend.administer.popup.domain.PopUpDto;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PopUpService implements PopUpSelectAllUseCase, PopUpSelectOneUseCase, PopUpRegistUseCase, PopUpUpdateUseCase, PopUpDeleteUseCase{



    @Override
    public PopUpDto selectOne(Long id) {
        return null;
    }

    @Override
    public List<PopUpDto> selectAll() {
        return null;
    }

    @Override
    public void deletePopUp(Long id) {
        
    }

    @Override
    public void update(PopUpDto dto) {
        
    }

    @Override
    public void regist(PopUpDto dto) {
        
    }
}

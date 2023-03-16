package com.arton.backend.administer.popup.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.arton.backend.administer.popup.application.in.PopUpDeleteUseCase;
import com.arton.backend.administer.popup.application.in.PopUpRegistUseCase;
import com.arton.backend.administer.popup.application.in.PopUpSelectAllUseCase;
import com.arton.backend.administer.popup.application.in.PopUpSelectOneUseCase;
import com.arton.backend.administer.popup.application.in.PopUpUpdateUseCase;
import com.arton.backend.administer.popup.application.out.PopUpDeletePort;
import com.arton.backend.administer.popup.application.out.PopUpRegistPort;
import com.arton.backend.administer.popup.application.out.PopUpSelectAllPort;
import com.arton.backend.administer.popup.application.out.PopUpSelectOnePort;
import com.arton.backend.administer.popup.domain.PopUpDto;
import com.arton.backend.administer.popup.domain.PopUpEntity;
import com.arton.backend.administer.popup.domain.PopUpMapper;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PopUpService implements PopUpSelectAllUseCase, PopUpSelectOneUseCase, PopUpRegistUseCase, PopUpUpdateUseCase, PopUpDeleteUseCase{

    private final PopUpRegistPort registPort;
    private final PopUpDeletePort deletePort;
    private final PopUpSelectAllPort selectAllPort;
    private final PopUpSelectOnePort selectOnePort;
    private final PopUpMapper mapper;

    @Override
    public PopUpDto selectOne(Long id) {
        return mapper.toDomain(
            selectOnePort.selectOne(id)
        );
    }

    @Override
    public List<PopUpDto> selectAll() {
        return null;
    }

    @Override
    public void deletePopUp(Long id) {
        deletePort.deleteOneById(id);
    }

    @Override
    public void update(PopUpDto dto) {
        PopUpEntity original = selectOnePort.selectOne(dto.getId());
        original.update(
            dto.getStarTime(),
            dto.getEndTime(),
            dto.isActive(),
            dto.getPositionX(),
            dto.getPositionY(),
            dto.getSize(),
            dto.getUrl(),
            dto.getContent()
        );
    }

    @Override
    public void regist(PopUpDto dto) {
        
    }
}

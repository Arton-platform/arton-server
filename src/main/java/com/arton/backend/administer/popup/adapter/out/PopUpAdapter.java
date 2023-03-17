package com.arton.backend.administer.popup.adapter.out;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.arton.backend.administer.popup.application.out.PopUpDeletePort;
import com.arton.backend.administer.popup.application.out.PopUpRegistPort;
import com.arton.backend.administer.popup.application.out.PopUpSelectAllPort;
import com.arton.backend.administer.popup.application.out.PopUpSelectOnePort;
import com.arton.backend.administer.popup.domain.PopUpEntity;
import com.arton.backend.administer.popup.domain.PopUpMapper;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PopUpAdapter implements PopUpSelectAllPort, PopUpSelectOnePort, PopUpRegistPort, PopUpDeletePort {
    
    private final PopUpRepository repository;
    private final PopUpMapper mapper;

    @Override
    public void deleteOneById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void save(PopUpEntity entity) {
        repository.save(entity);
    }

    @Override
    public PopUpEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomException(
                    ErrorCode.SELECT_ERROR.getMessage(), 
                    ErrorCode.SELECT_ERROR)
                );
        
    }

    @Override
    public List<PopUpEntity> findAll() {
        return Optional.ofNullable(repository.findAll()).orElseGet(ArrayList::new);
    }
    
}

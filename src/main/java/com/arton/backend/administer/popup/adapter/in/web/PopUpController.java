package com.arton.backend.administer.popup.adapter.in.web;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arton.backend.administer.popup.application.in.PopUpDeleteUseCase;
import com.arton.backend.administer.popup.application.in.PopUpRegistUseCase;
import com.arton.backend.administer.popup.application.in.PopUpSelectAllUseCase;
import com.arton.backend.administer.popup.application.in.PopUpSelectOneUseCase;
import com.arton.backend.administer.popup.application.in.PopUpUpdateUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/popUp")
@RequiredArgsConstructor
public class PopUpController {
    // 등록, 수정, 삭제, 조회
    private final PopUpRegistUseCase registUseCase;
    private final PopUpUpdateUseCase updateUseCase;
    private final PopUpDeleteUseCase deleteUseCase;
    private final PopUpSelectAllUseCase selectAllUseCase;
    private final PopUpSelectOneUseCase selectOneUseCase;
}

package com.arton.backend.administer.category.adapter.in;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arton.backend.administer.category.application.in.CategoryDeleteUseCase;
import com.arton.backend.administer.category.application.in.CategoryFindAllUseCase;
import com.arton.backend.administer.category.application.in.CategoryFindOneUseCase;
import com.arton.backend.administer.category.application.in.CategoryRegistUseCase;
import com.arton.backend.administer.category.application.in.CategoryUpdateUseCase;
import com.arton.backend.administer.category.domain.dtos.CategoryDto;
import com.arton.backend.administer.category.domain.dtos.CategoryRegistDto;
import com.arton.backend.administer.category.domain.dtos.CategoryUpdateDto;
import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;

import lombok.RequiredArgsConstructor;

/*
 * 카테고리별로 공연 뮤지컬 등을 관리하기 위한 클래스(등록, 수정, 삭제, 조회)
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryFindOneUseCase findOneUseCase;
    private final CategoryFindAllUseCase findAllUseCase;
    private final CategoryRegistUseCase registUseCase;
    private final CategoryUpdateUseCase updateUseCase;
    private final CategoryDeleteUseCase deleteUseCase;
    
    @GetMapping("/find/{id}")
    public ResponseData<CategoryDto> categoryfindOne(Long id){
        return new ResponseData<>(
            "SUCCESS",
            HttpStatus.OK.value(),
            findOneUseCase.findOne(id)
        );
    }

    @GetMapping("/find")
    public ResponseData<List<CategoryDto>> categoryfindAll(){
        return new ResponseData<>(
            "SUCCESS",
            HttpStatus.OK.value(),
            findAllUseCase.findAll()
        );
    }
    
    @PostMapping("/regist")
    public CommonResponse categoryRegist(CategoryRegistDto registDto){
        
        registUseCase.regist(registDto);
        
        return CommonResponse.builder()
        .message("SUCCESS")
        .status(HttpStatus.OK.value())
        .build();
    }
    
    @PatchMapping("/update")
    public CommonResponse categoryUpdate(CategoryUpdateDto updateDto){

        updateUseCase.update(updateDto);

        return CommonResponse.builder()
        .message("SUCCESS")
        .status(HttpStatus.OK.value())
        .build();
    }
    
    @DeleteMapping("/delete")
    public CommonResponse categoryDelete(Long id){

        deleteUseCase.delete(id);

        return CommonResponse.builder()
        .message("SUCCESS")
        .status(HttpStatus.OK.value())
        .build();
    }
}

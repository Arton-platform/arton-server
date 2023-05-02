package com.arton.backend.administer.admin.adapter.in.web;
import java.util.List;

import com.arton.backend.administer.admin.application.data.AdminSignupDTO;
import com.arton.backend.auth.application.data.LoginRequestDto;
import com.arton.backend.auth.application.data.TokenDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.arton.backend.administer.admin.application.port.in.AdminUserUseCase;
import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.user.domain.User;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {
    
    private final AdminUserUseCase adminUserUseCase;

    // 관리자 등록
    @PostMapping("/regist")
    public CommonResponse regist(User user){
        adminUserUseCase.regist(user);
        return CommonResponse.builder()
            .message("SUCCESS")
            .status(HttpStatus.OK.value())
            .build();
    }

    @PostMapping("/regist/v2")
    public CommonResponse registV2(AdminSignupDTO user){
        adminUserUseCase.registV2(user);
        return CommonResponse.builder()
                .message("SUCCESS")
                .status(HttpStatus.OK.value())
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(adminUserUseCase.login(loginRequestDto));
    }

    // 관리자 조회
    @GetMapping("/find/{id}")
    public ResponseData<User> findAdmin(@PathVariable("id") Long id){
        return new ResponseData<>(
            "SUCCESS", 
            HttpStatus.OK.value(), 
            adminUserUseCase.findAdmin(id)
        );
    }

    // 관리자 일괄 조회
    @GetMapping("/findAll")
    public ResponseData<List<User>> findAll(){
        return new ResponseData<>(
            "SUCCESS",
            HttpStatus.OK.value(),
            adminUserUseCase.findAll()
        );
    }

    // 관리자 수정
    @PostMapping("/update")
    public CommonResponse update(User user){
        
        adminUserUseCase.update(user);

        return CommonResponse.builder()
            .message("SUCCESS")
            .status(HttpStatus.OK.value())
            .build();
    }
    
    // 관리자 삭제
    //    - 삭제의 경우는 관리자가 1명이라도 없으면 한됨
    //    - 관리자 리스트를 조회 한 후에 관리자의 총 숫자가 1명 이하이면 "관리자는 최소 1명 이상 존재해야 합니다. 경고"
    @DeleteMapping("/delete")
    public CommonResponse delete(User user){
        adminUserUseCase.delete(user);
        return CommonResponse.builder()
            .message("SUCCESS")
            .status(HttpStatus.OK.value())
            .build();
    }

}

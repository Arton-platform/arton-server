package com.arton.backend.administer.admin.adapter.in.web;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arton.backend.administer.admin.application.port.in.AdminUserUseCase;
import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.user.domain.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {
    
    private final AdminUserUseCase adminUserUseCase;

    // 관리자 등록
    @RequestMapping("/regist")
    public CommonResponse regist(User user){
        adminUserUseCase.regist(user);
        return CommonResponse.builder()
            .message("SUCCESS")
            .status(HttpStatus.OK.value())
            .build();
    }
    
    // 관리자 조회
    @RequestMapping("/findAdmin/{id}")
    public ResponseData<User> findAdmin(@PathVariable("id") Long id){
        return new ResponseData<>(
            "SUCCESS", 
            HttpStatus.OK.value(), 
            adminUserUseCase.findAdmin(id)
        );
    }

    // 관리자 수정
    // 관리자 삭제
    //    - 삭제의 경우는 관리자가 1명이라도 없으면 한됨
    //    - 관리자 리스트를 조회 한 후에 관리자의 총 숫자가 1명 이하이면 "관리자는 최소 1명 이상 존재해야 합니다. 경고"

}

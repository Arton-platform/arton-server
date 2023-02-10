package com.arton.backend.administer.user.adapter.in.web;

import com.arton.backend.administer.user.adapter.in.web.dto.UserDeleteByAdminDto;
import com.arton.backend.administer.user.adapter.in.web.dto.UserRegisterByAdminDto;
import com.arton.backend.administer.user.adapter.in.web.dto.UserUpdateByAdminDto;
import com.arton.backend.administer.user.application.port.in.ManageUserUseCase;
import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.user.domain.User;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class ManageUserController {

    ManageUserUseCase manageUserUseCase;

    @GetMapping("/all-users")
    public ResponseEntity<ResponseData<List<User>>> allUser(){
        ResponseData<List<User>> response = new ResponseData<List<User>>(
                "SUCCESS",
                HttpStatus.OK.value(),
                manageUserUseCase.allUser()
        );
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/user-detail/{id}")
    public ResponseEntity<ResponseData<User>> userDetail(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(
                new ResponseData<User>(
                        "SUCCESS",
                        HttpStatus.OK.value(),
                        manageUserUseCase.userDetail(id)
                )
        );
    }

    @PostMapping("/register-user-admin")
    public ResponseEntity<CommonResponse> registerUserByAdmin(UserRegisterByAdminDto userRegisterByAdminDto){
        manageUserUseCase.registerUserByAdmin(userRegisterByAdminDto);
        return ResponseEntity.ok().body(
            CommonResponse.builder()
                    .message("SUCCESS")
                    .status(HttpStatus.OK.value())
                    .build()
        );
    }

    @PutMapping("/update-user-admin")
    public ResponseEntity<CommonResponse> updateUserByAdmin(UserUpdateByAdminDto userUpdateByAdminDto){
        manageUserUseCase.updateUserByAdmin(userUpdateByAdminDto);
        return ResponseEntity.ok().body(
                CommonResponse.builder()
                        .message("SUCCESS")
                        .status(HttpStatus.OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete-user-admin")
    public ResponseEntity<CommonResponse> deleteUserByAdmin(UserDeleteByAdminDto userDeleteByAdminDto){
        manageUserUseCase.deleteUserByAdmin(userDeleteByAdminDto);
        return ResponseEntity.ok().body(
                CommonResponse.builder()
                        .message("SUCCESS")
                        .status(HttpStatus.OK.value())
                        .build()
        );
    }

}

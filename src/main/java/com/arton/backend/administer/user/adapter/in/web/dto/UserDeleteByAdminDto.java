package com.arton.backend.administer.user.adapter.in.web.dto;

import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDeleteByAdminDto {
    Long id;

    public UserEntity toEntity(){
        return UserEntity.builder()
                .id(id)
                .build();
    }
}

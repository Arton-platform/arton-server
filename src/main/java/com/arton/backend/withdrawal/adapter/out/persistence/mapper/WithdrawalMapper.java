package com.arton.backend.withdrawal.adapter.out.persistence.mapper;

import com.arton.backend.user.adapter.out.persistence.mapper.UserMapper;
import com.arton.backend.withdrawal.adapter.out.persistence.entity.WithdrawalEntity;
import com.arton.backend.withdrawal.domain.Withdrawal;

public class WithdrawalMapper {

    public static Withdrawal toDomain(WithdrawalEntity withdrawal) {
        return Withdrawal.builder()
                .id(withdrawal.getId())
                .comment(withdrawal.getComment())
                .createdDate(withdrawal.getCreatedDate())
                .updateDate(withdrawal.getUpdatedDate())
                .user(UserMapper.toDomain(withdrawal.getUser()))
                .build();
    }

    public static WithdrawalEntity toEntity(Withdrawal withdrawal) {
        return WithdrawalEntity.builder()
                .id(withdrawal.getId())
                .comment(withdrawal.getComment())
                .createdDate(withdrawal.getCreatedDate())
                .updateDate(withdrawal.getUpdatedDate())
                .user(UserMapper.toEntity(withdrawal.getUser()))
                .build();
    }
}

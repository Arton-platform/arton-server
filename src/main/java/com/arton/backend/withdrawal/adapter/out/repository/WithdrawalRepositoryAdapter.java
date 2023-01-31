package com.arton.backend.withdrawal.adapter.out.repository;

import com.arton.backend.user.adapter.out.repository.UserMapper;
import com.arton.backend.user.domain.User;
import com.arton.backend.withdrawal.application.port.out.GetWithdrawalPort;
import com.arton.backend.withdrawal.application.port.out.WithdrawalRegistPort;
import com.arton.backend.withdrawal.domain.Withdrawal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WithdrawalRepositoryAdapter implements GetWithdrawalPort, WithdrawalRegistPort {
    private final WithdrawalRepository withdrawalRepository;

    @Override
    public Withdrawal save(Withdrawal withdrawal) {
        return WithdrawalMapper.toDomain(withdrawalRepository.save(WithdrawalMapper.toEntity(withdrawal)));
    }

    @Override
    public Optional<Withdrawal> findByUser(User user) {
       return withdrawalRepository.findByUser(UserMapper.toEntity(user)).map(WithdrawalMapper::toDomain);
    }

    @Override
    public Optional<Withdrawal> findByUserId(Long userId) {
        return withdrawalRepository.findByUser_Id(userId).map(WithdrawalMapper::toDomain);
    }
}

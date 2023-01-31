package com.arton.backend.withdrawal.adapter.out.repository;

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
        return null;
    }

    @Override
    public Optional<Withdrawal> findByUser(User User) {
        return Optional.empty();
    }

    @Override
    public Optional<Withdrawal> findByUserId(Long userId) {
        return Optional.empty();
    }
}

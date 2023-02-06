package com.arton.backend.withdrawal.application.port.out;

import com.arton.backend.user.domain.User;
import com.arton.backend.withdrawal.domain.Withdrawal;

import java.util.Optional;

public interface GetWithdrawalPort {
    Optional<Withdrawal> findByUser(User User);
    Optional<Withdrawal> findByUserId(Long userId);
}

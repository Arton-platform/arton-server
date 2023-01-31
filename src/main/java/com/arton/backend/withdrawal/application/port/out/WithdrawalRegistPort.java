package com.arton.backend.withdrawal.application.port.out;

import com.arton.backend.withdrawal.domain.Withdrawal;

public interface WithdrawalRegistPort {
    Withdrawal save(Withdrawal withdrawal);
}

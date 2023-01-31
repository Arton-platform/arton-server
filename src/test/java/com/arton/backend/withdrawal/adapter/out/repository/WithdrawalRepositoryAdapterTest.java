package com.arton.backend.withdrawal.adapter.out.repository;

import com.arton.backend.user.domain.User;
import com.arton.backend.withdrawal.application.port.out.GetWithdrawalPort;
import com.arton.backend.withdrawal.domain.Withdrawal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WithdrawalRepositoryAdapterTest {
    @Autowired
    GetWithdrawalPort withdrawalRepository;

    @Test
    void nullTest() {
        Withdrawal withdrawal = withdrawalRepository.findByUserId(22L).orElseThrow(() -> new RuntimeException("존재하지 않음."));
        System.out.println("withdrawal = " + withdrawal);
    }

    @Test
    void nullTest2() {
        Assertions.assertThrows(NoSuchElementException.class, () -> withdrawalRepository.findByUser(User.builder().id(22L).build()).get());
    }
}
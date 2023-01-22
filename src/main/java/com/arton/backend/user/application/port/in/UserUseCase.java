package com.arton.backend.user.application.port.in;

public interface UserUseCase {
    void changePassword(Long userID, UserPasswordEditDto editDto);
}

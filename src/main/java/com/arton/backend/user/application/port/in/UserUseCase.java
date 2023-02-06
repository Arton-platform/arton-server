package com.arton.backend.user.application.port.in;

import com.arton.backend.user.application.data.UserPasswordEditDto;

public interface UserUseCase {
    void changePassword(Long userID, UserPasswordEditDto editDto);
    Boolean alertState(long userId);
    void updateAlertState(long userId, Boolean state);
}

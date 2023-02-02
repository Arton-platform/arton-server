package com.arton.backend.user.application.port.out;

public interface UserValidRepositoryPort {
    /** 이메일 중복 여부 확인*/
    boolean checkEmailDup(String email);
}

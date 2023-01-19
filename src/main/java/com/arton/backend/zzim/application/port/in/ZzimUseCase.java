package com.arton.backend.zzim.application.port.in;

public interface ZzimUseCase {
    void deleteUsersFavorite(Long userId, ZzimDeleteDto deleteDto);
}

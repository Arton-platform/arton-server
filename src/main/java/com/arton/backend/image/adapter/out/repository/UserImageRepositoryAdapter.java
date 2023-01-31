package com.arton.backend.image.adapter.out.repository;

import com.arton.backend.image.application.port.out.UserImageRepositoryPort;
import com.arton.backend.image.application.port.out.UserImageSaveRepositoryPort;
import com.arton.backend.image.domain.UserImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.arton.backend.image.adapter.out.repository.UserImageMapper.toDomain;


@Repository
@RequiredArgsConstructor
public class UserImageRepositoryAdapter implements UserImageSaveRepositoryPort, UserImageRepositoryPort {
    private final UserImageRepository userImageRepository;
    @Override
    public UserImage save(UserImage userImage) {
        return toDomain(userImageRepository.save(UserImageMapper.toEntity(userImage)));
    }

    @Override
    public Optional<UserImage> findUserImage(Long id) {
        Optional<UserImageEntity> response = userImageRepository.findById(id);
        if (response.isPresent()) {
            return Optional.ofNullable(toDomain(response.get()));
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<UserImage> findUserImageByUser(Long userId) {
        Optional<UserImageEntity> response = userImageRepository.findByUser_id(userId);
        if (response.isPresent()) {
            return Optional.ofNullable(toDomain(response.get()));
        }
        return Optional.ofNullable(null);
    }

    @Override
    public void delete(Long id) {
        userImageRepository.deleteById(id);
    }

    @Override
    public void deleteByUserId(Long userId) {
        userImageRepository.deleteByUser_Id(userId);
    }
}

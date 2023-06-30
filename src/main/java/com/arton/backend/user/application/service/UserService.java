package com.arton.backend.user.application.service;

import com.arton.backend.follow.applicaion.port.out.FollowRepositoryPort;
import com.arton.backend.image.application.port.out.UserImageRepositoryPort;
import com.arton.backend.image.application.port.out.UserImageSaveRepositoryPort;
import com.arton.backend.image.domain.UserImage;
import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import com.arton.backend.review.adapter.out.persistence.mapper.ReviewMapper;
import com.arton.backend.review.application.data.CommonReviewDto;
import com.arton.backend.review.application.data.CommonReviewDtoWithOutChilds;
import com.arton.backend.review.application.data.CommonReviewQueryDslDto;
import com.arton.backend.review.application.data.ReviewDto;
import com.arton.backend.review.application.port.out.ReviewCountPort;
import com.arton.backend.review.application.port.out.ReviewListPort;
import com.arton.backend.user.application.data.MyPageDto;
import com.arton.backend.user.application.data.UserPasswordEditDto;
import com.arton.backend.user.application.data.UserProfileEditDto;
import com.arton.backend.user.application.port.in.MyPageUseCase;
import com.arton.backend.user.application.port.in.UserUseCase;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserUseCase, MyPageUseCase {

    private final UserRepositoryPort userRepository;
    private final ReviewCountPort reviewCountPort;
    private final ReviewListPort reviewListPort;
    private final FollowRepositoryPort followRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final FileUploadUtils fileUploadUtils;
    private final ReviewMapper reviewMapper;
    private final UserImageSaveRepositoryPort userImageSaveRepository;
    private final UserImageRepositoryPort userImageRepository;
    @Value("${spring.user.image.dir}")
    private String imageDir;

    @Override
    public void changePassword(Long userId, UserPasswordEditDto editDto) {
        User user = findUser(userId);
        if (!checkPassword(editDto.getPassword(), editDto.getCheckPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH.getMessage(), ErrorCode.PASSWORD_NOT_MATCH);
        }
        user.setPassword(passwordEncoder.encode(editDto.getPassword()));
        userRepository.save(user);
    }

    public boolean checkPassword(String password, String checkPassword){
        return password.equals(checkPassword);
    }

    private User findUser(Long userId) {
       return userRepository.findById(userId).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public Boolean alertState(long userId) {
        return findUser(userId).getAlertState();
    }

    /**
     * ART-21
     * 닉네임, 한줄소개, 이미지를 업데이트
     * @param userId
     * @param userProfileEditDto
     * @param multipartFile
     */
    @Override
    public void updateUserProfile(long userId, UserProfileEditDto userProfileEditDto, MultipartFile multipartFile) {
        User user = findUser(userId);
        // 이미지 업데이트
        // 무조건 기본적으로 디폴트 이미지 넣어서 매핑시켜주기 때문에
        // UserImage null 이 아님.
        if (multipartFile != null) {
            UserImage userImage = userImageRepository.findUserImageByUser(user.getId()).orElseGet(null);
            fileUploadUtils.deleteFile(user.getId(), userImage.getImageUrl());
            String upload = fileUploadUtils.upload(multipartFile, imageDir + user.getId());
            userImage.updateImage(upload);
            UserImage savedImage = userImageSaveRepository.save(userImage);
            user.setImage(savedImage);
        }
        user.updateProfile(userProfileEditDto);
        userRepository.save(user);
    }

    @Override
    public void updateAlertState(long userId, Boolean state) {
        User user = findUser(userId);
        user.changeAlertState(state);
    }

    /**
     * 마이페이지 화면 return
     * @param userId
     * @return
     */
    @Override
    public MyPageDto getMyPageInfo(long userId) {
        User user = findUser(userId);
        UserImage userImage = userImageRepository.findUserImageByUser(userId).orElseThrow(() -> new CustomException(ErrorCode.IMAGE_LOAD_FAILED.getMessage(), ErrorCode.IMAGE_LOAD_FAILED));
        String nickname = user.getNickname();
        String selfDescription = user.getSelfDescription();
        String profileImageUrl = userImage.getImageUrl();
        Long followersCount = followRepositoryPort.getFollowersCount(userId);
        Long followingsCount = followRepositoryPort.getFollowingsCount(userId);
        Long userReviewCount = reviewCountPort.getUserReviewCount(userId);
        // 수정된 리뷰 수집기.
        // 대댓글 연동해서 갯수 구현 해야함.
        List<CommonReviewDtoWithOutChilds> reviews = reviewListPort.getUserReviewList(userId).stream().map(CommonReviewQueryDslDto::toDtoWithOutChilds).collect(Collectors.toList());
        reviews.stream().forEach(commonReviewDtoWithOutChilds -> {
            commonReviewDtoWithOutChilds.setReviewCount(reviewCountPort.getChildReviewCount(commonReviewDtoWithOutChilds.getId()));
        });
        return MyPageDto.builder()
                .id(userId)
                .nickname(nickname)
                .selfDescription(selfDescription)
                .profileImageUrl(profileImageUrl)
                .followers(followersCount)
                .followings(followingsCount)
                .reviewCount(userReviewCount)
                .reviews(reviews)
                .build();
    }
}

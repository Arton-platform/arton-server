package com.arton.backend.user.application.service;

import com.arton.backend.follow.applicaion.port.out.FollowRepositoryPort;
import com.arton.backend.image.application.port.out.UserImageRepositoryPort;
import com.arton.backend.image.application.port.out.UserImageSaveRepositoryPort;
import com.arton.backend.image.domain.UserImage;
import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.review.adapter.out.persistence.ReviewMapper;
import com.arton.backend.review.application.port.in.MyPageReviewDto;
import com.arton.backend.review.application.port.out.ReviewCountPort;
import com.arton.backend.review.application.port.out.ReviewListPort;
import com.arton.backend.review.domain.Review;
import com.arton.backend.user.adapter.out.repository.UserMapper;
import com.arton.backend.user.application.data.MyPageDto;
import com.arton.backend.user.application.data.UserPasswordEditDto;
import com.arton.backend.user.application.data.UserProfileEditDto;
import com.arton.backend.user.application.port.in.*;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        if (multipartFile != null) {
            UserImage userImage = userImageRepository.findUserImageByUser(user.getId()).get();
            fileUploadUtils.delete(user.getId(), userImage.getImageUrl());
            String upload = fileUploadUtils.upload(multipartFile, "arton/image/profiles/" + user.getId());
            userImage.updateImage(upload);
            user.setImage(userImage);
            userImageSaveRepository.save(userImage);
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

        List<Review> userReviews = reviewListPort.userReviewList(UserMapper.toEntity(user)).map(reviews ->
                reviews.stream().map(review -> reviewMapper.toDomain(review))
                        .collect(Collectors.toList())).orElseGet(Collections::emptyList);
        List<MyPageReviewDto> collect = userReviews.stream().map(MyPageReviewDto::to).collect(Collectors.toList());
        for (MyPageReviewDto myPageReviewDto : collect) {
            myPageReviewDto.setReviewCount(reviewCountPort.getPerformanceReviewCount(myPageReviewDto.getPerformanceId()));
        }
        return MyPageDto.builder()
                .id(userId)
                .nickname(nickname)
                .selfDescription(selfDescription)
                .profileImageUrl(profileImageUrl)
                .followers(followersCount)
                .followings(followingsCount)
                .reviewCount(userReviewCount)
                .reviews(collect)
                .build();
    }
}

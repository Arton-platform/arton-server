package com.arton.backend.review.application.service;

import com.arton.backend.image.application.port.out.ReviewImageRepositoryPort;
import com.arton.backend.image.application.port.out.ReviewImageSaveRepositoryPort;
import com.arton.backend.image.domain.PerformanceImage;
import com.arton.backend.image.domain.ReviewImage;
import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import com.arton.backend.review.application.data.*;
import com.arton.backend.review.application.port.in.*;
import com.arton.backend.review.application.port.out.*;
import com.arton.backend.review.domain.Review;
import com.arton.backend.reviewhit.application.service.ReviewHitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService implements ReviewListUseCase, ReviewRegistUseCase, ReviewCountUseCase, ReviewDeleteUseCase, ReviewEditUseCase, ReviewHitRemoveUseCase, ReviewHitAddUseCase {
    private final ReviewListPort reviewListPort;
    private final ReviewRegistPort reviewRegistPort;
    private final ReviewCountPort reviewCountPort;
    private final ReviewDeletePort reviewDeletePort;
    private final ReviewFindPort reviewFindPort;
    private final PerformanceRepositoryPort performanceRepositoryPort;
    private final ReviewHitService reviewHitService;
    private final FileUploadUtils fileUploadUtils;
    private final ReviewImageSaveRepositoryPort reviewImageSaveRepositoryPort;
    private final ReviewImageRepositoryPort reviewImageRepositoryPort;
    private final static Logger log = LoggerFactory.getLogger("LOGSTASH");
    @Value("${spring.review.image.dir}")
    private String reviewImageDir;

    /**
     * 공연의 모든 댓글 정보를 반환합니다.
     * @param performanceId
     * @return
     */
    @Override
    public ReviewForPerformanceDetailDto reviewList(Long performanceId) {
        Performance performance = performanceRepositoryPort.findById(performanceId).orElseThrow(() -> new CustomException(ErrorCode.PERFORMANCE_NOT_FOUND.getMessage(), ErrorCode.PERFORMANCE_NOT_FOUND));
        List<ReviewEntity> reviews = reviewListPort.reviewList(performanceId);
        List<CommonReviewDto> reviewResponse = new ArrayList<>();
        List<String> images = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        Map<Long, CommonReviewDto> map = new HashMap<>();
        reviews.stream().forEach(c -> {
                    ids.add(c.getId());
                    CommonReviewDto reviewDto = CommonReviewDto.toDtoFromEntity(c);
                    if (c.getParent() != null) {
                        reviewDto.setParentId(c.getParent().getId());
                    }
                    map.put(reviewDto.getId(), reviewDto);
                    if (c.getParent() != null) {
                        map.get(c.getParent().getId()).getChilds().add(reviewDto);
                    } else {
                        reviewResponse.add(reviewDto);
                    }
                }
        );
        for (Long id : ids) {
            List<String> reviewImages = reviewImageRepositoryPort.findByReviewId(id).stream().map(ReviewImage::getImageUrl).collect(Collectors.toList());
            images.addAll(reviewImages);
        }
        return ReviewForPerformanceDetailDto.builder().reviews(reviewResponse).images(images).build();
    }

    @Override
    public List<CommonReviewDto> getReviewChilds(Long reviewId) {
        reviewFindPort.findById(reviewId).orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND.getMessage(), ErrorCode.REVIEW_NOT_FOUND));
        List<ReviewEntity> reviews = reviewListPort.getReviewChilds(reviewId);
        List<CommonReviewDto> reviewResponse = new ArrayList<>();
        Map<Long, CommonReviewDto> map = new HashMap<>();
        reviews.stream().forEach(c -> {
            CommonReviewDto reviewDto = CommonReviewDto.toDtoFromEntity(c);
                    if (c.getParent() != null) {
                        reviewDto.setParentId(c.getParent().getId());
                    }
                    map.put(reviewDto.getId(), reviewDto);
                    if (c.getParent() != null) {
                        map.get(c.getParent().getId()).getChilds().add(reviewDto);
                    } else {
                        reviewResponse.add(reviewDto);
                    }
                }
        );
        return reviewResponse;
    }

    @Override
    public void regist(long userId, ReviewCreateDto reviewCreateDto) {
        log.info("review regist {}", reviewCreateDto);
        // validation
        if (reviewCreateDto.getPerformanceId() == null || reviewCreateDto.getStarScore() == null){
            throw new CustomException(ErrorCode.BAD_REQUEST.getMessage(), ErrorCode.BAD_REQUEST);
        }
        log.info("performance check start");
        // performance check
        performanceRepositoryPort.findById(reviewCreateDto.getPerformanceId()).orElseThrow(() -> new CustomException(ErrorCode.PERFORMANCE_NOT_FOUND.getMessage(), ErrorCode.PERFORMANCE_NOT_FOUND));
        log.info("performance check finish");
        // parent check
        Review parent = null;
        if (reviewCreateDto.getParentId() != null){
            log.info("parent review {}", reviewCreateDto.getParentId());
            // 해당 부모아이디인 리뷰 id 가 존재하는지 체크
            parent = reviewFindPort.findById(reviewCreateDto.getParentId()).orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND.getMessage(), ErrorCode.REVIEW_NOT_FOUND));
            if (parent.getPerformanceId() != reviewCreateDto.getPerformanceId()) {
                throw new CustomException(ErrorCode.REVIEW_PERFORMANCE_NOT_MATCHED.getMessage(), ErrorCode.REVIEW_PERFORMANCE_NOT_MATCHED);
            }
        }
        Review review = reviewCreateDto.toDomain();
        review.setUserId(userId);
        if (parent != null) {
            review.updateParent(parent);
        }
        reviewRegistPort.regist(review);
    }

    @Override
    public void regist(long userId, ReviewCreateDto reviewCreateDto, List<MultipartFile> multipartFileList) {
        // first image count check
        if (multipartFileList != null && multipartFileList.size() > 5) {
            throw new CustomException(ErrorCode.UPLOAD_COUNT_LIMIT.getMessage(), ErrorCode.UPLOAD_COUNT_LIMIT);
        }
        // regist revie first
        log.info("review regist {}", reviewCreateDto);
        // validation
        if (reviewCreateDto.getPerformanceId() == null || reviewCreateDto.getStarScore() == null){
            throw new CustomException(ErrorCode.BAD_REQUEST.getMessage(), ErrorCode.BAD_REQUEST);
        }
        log.info("performance check start");
        // performance check
        performanceRepositoryPort.findById(reviewCreateDto.getPerformanceId()).orElseThrow(() -> new CustomException(ErrorCode.PERFORMANCE_NOT_FOUND.getMessage(), ErrorCode.PERFORMANCE_NOT_FOUND));
        log.info("performance check finish");
        // parent check
        Review parent = null;
        if (reviewCreateDto.getParentId() != null){
            log.info("parent review {}", reviewCreateDto.getParentId());
            // 해당 부모아이디인 리뷰 id 가 존재하는지 체크
            parent = reviewFindPort.findById(reviewCreateDto.getParentId()).orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND.getMessage(), ErrorCode.REVIEW_NOT_FOUND));
            if (parent.getPerformanceId() != reviewCreateDto.getPerformanceId()) {
                throw new CustomException(ErrorCode.REVIEW_PERFORMANCE_NOT_MATCHED.getMessage(), ErrorCode.REVIEW_PERFORMANCE_NOT_MATCHED);
            }
        }
        Review review = reviewCreateDto.toDomain();
        review.setUserId(userId);
        if (parent != null) {
            review.updateParent(parent);
        }
        Review savedReview = reviewRegistPort.regist(review);
        if (multipartFileList != null) {
            boolean isEmpty = multipartFileList.stream().filter(multipartFile -> !multipartFile.isEmpty()).count() == 0;
            List<ReviewImage> reviewImages = new ArrayList<>();
            // 이미지가 있다면
            if (!isEmpty) {
                for (MultipartFile image : multipartFileList) {
                    if (image.isEmpty())
                        continue;
                    String upload = fileUploadUtils.upload(image, reviewImageDir + savedReview.getId());

                    reviewImages.add(ReviewImage.builder()
                            .reviewId(savedReview.getId())
                            .imageUrl(upload)
                            .build());
                }
                reviewImageSaveRepositoryPort.saveAll(reviewImages);
            }
        }
    }

    @Override
    public Long performanceReviewCount(Long id) {
        return reviewCountPort.getPerformanceReviewCount(id);
    }

    @Override
    public Long userReviewCount(Long id) {
        return reviewCountPort.getUserReviewCount(id);
    }

    @Override
    public void delete(long userId, long reviewId) {
        boolean userHasReview = reviewFindPort.userHasReview(reviewId, userId);
        if (!userHasReview) {
            throw new CustomException(ErrorCode.REVIEW_NOT_FOUND.getMessage(), ErrorCode.REVIEW_NOT_FOUND);
        }
        reviewDeletePort.deleteReview(reviewId);
    }

    @Override
    public void deleteAllReviews(long userId) {
        // delete
        reviewDeletePort.deleteUserAllReview(userId);
    }

    @Override
    public void edit(long userId, ReviewEditDto reviewEditDto) {
        if (reviewEditDto.getId() == null) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
        Review review = reviewFindPort.findByIdAndUserId(reviewEditDto.getId(), userId).orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND.getMessage(), ErrorCode.REVIEW_NOT_FOUND));
        review.editReview(reviewEditDto);
        // update
        reviewRegistPort.regist(review);
    }

    @Override
    public void addHit(long userId, long reviewId) {
        // review 있는지
        Review review = reviewFindPort.findById(reviewId).orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND.getMessage(), ErrorCode.REVIEW_NOT_FOUND));
        // review hit
        reviewHitService.addHit(userId, reviewId);
        // review count
        review.addHit();
        //update
        reviewRegistPort.regist(review);
    }

    @Override
    public void removeHit(long userId, long reviewId) {
        // review 있는지
        Review review = reviewFindPort.findById(reviewId).orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND.getMessage(), ErrorCode.REVIEW_NOT_FOUND));
        // review hit
        reviewHitService.removeHit(userId, reviewId);
        // review count
        review.decreaseHit();
        //update
        reviewRegistPort.regist(review);
    }
}

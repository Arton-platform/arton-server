package com.arton.backend.user.domain;

import com.arton.backend.image.domain.UserImage;
import com.arton.backend.user.application.data.UserProfileEditDto;
import com.arton.backend.zzim.domain.ArtistZzim;
import com.arton.backend.zzim.domain.PerformanceZzim;
import lombok.*;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User {
    private Long id;
    /** 카카오 고유 회원 번호 */
    private Long kakaoId;
    /** naver 고유 회원 번호 */
    private String naverId;
    /** 이메일 */
    private String email;
    /** 비밀번호 */
    private String password;
    /** 닉네임 */
    private String nickname;
    /** 성별 */
    private Gender gender;
    /** 연령대 */
    private AgeRange ageRange;
    /** 권한 */
    private UserRole auth;
    /** 회원가입 수단 */
    private SignupType signupType;
    /** 마케팅 동의 */
    private String termsAgree;
    /** 가입일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updatedDate;
    /** 찜 목록 유저는 찜을 여러개 할 수 있음*/
    @ToString.Exclude
    List<PerformanceZzim> performanceZzims = new ArrayList<>();
    @ToString.Exclude
    List<ArtistZzim> artistZzims = new ArrayList<>();
    private Boolean alertState;
    /** 회원 여부 */
    private Boolean userStatus = true;
    private String selfDescription;
    private UserImage userImage;
    public void setImage(UserImage userImage){
        this.userImage = userImage;
    }
    /**
     * 아티스트를 찜한다
     * @param artistZzim
     */
    public void zzimArtist(ArtistZzim artistZzim) {
        artistZzims.add(artistZzim);
        artistZzim.setUser(this.getId());
    }
    /**
     * 공연을 찜한다
     * @param performanceZzim
     */
    public void zzimPerformance(PerformanceZzim performanceZzim) {
        performanceZzims.add(performanceZzim);
        performanceZzim.setUser(this.id);
    }
    public void setPassword(String newPassword){
        this.password = newPassword;
    }
    public void changeAlertState(Boolean state) {
        this.alertState = state;
    }
    public void changeUserStatus(boolean status) {
        this.userStatus = status;
    }
    public void setSelfDescription(String selfDescription) {this.selfDescription = selfDescription;}
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 프로필 업데이트
     * @param userProfileEditDto
     */
    public void updateProfile(UserProfileEditDto userProfileEditDto) {
        if (userProfileEditDto != null) {
            // 컨트롤러에서 이미 검증이 됨.
            if (StringUtils.hasText(userProfileEditDto.getNickname())) {
                this.nickname = userProfileEditDto.getNickname();
            }
            if (StringUtils.hasText(userProfileEditDto.getSelfDescription())) {
                this.selfDescription = userProfileEditDto.getSelfDescription();
            }
        }
    }
    @Builder
    public User(Long id, Long kakaoId, String naverId, String email, String password, String nickname, Gender gender, AgeRange ageRange, UserRole auth, SignupType signupType, String termsAgree, LocalDateTime createdDate, LocalDateTime updateDate, List<PerformanceZzim> performanceZzims, List<ArtistZzim> artistZzims, String selfDescription, UserImage userImage, Boolean userStatus) {
        this.id = id;
        this.kakaoId = kakaoId;
        this.naverId = naverId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.ageRange = ageRange;
        this.auth = auth;
        this.signupType = signupType;
        this.termsAgree = termsAgree;
        this.createdDate = createdDate;
        this.updatedDate = updateDate;
        this.performanceZzims = performanceZzims;
        this.artistZzims = artistZzims;
        this.alertState = true;
        this.selfDescription = selfDescription;
        this.userImage = userImage;
        this.userStatus = userStatus;
    }
}

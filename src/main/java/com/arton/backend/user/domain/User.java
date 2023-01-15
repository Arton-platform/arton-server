package com.arton.backend.user.domain;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.zzim.adapter.out.repository.ArtistZzimEntity;
import com.arton.backend.zzim.adapter.out.repository.PerformanceZzimEntity;
import com.arton.backend.zzim.domain.ArtistZzim;
import com.arton.backend.zzim.domain.PerformanceZzim;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    /** 프로필 이미지 링크 */
    private String profileImageUrl;
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
    private LocalDateTime updateDate;
    /** 찜 목록 유저는 찜을 여러개 할 수 있음*/
    @ToString.Exclude
    List<PerformanceZzim> performanceZzims = new ArrayList<>();
    @ToString.Exclude
    List<ArtistZzim> artistZzims = new ArrayList<>();

    public void setProfileImageUrl(String url){
        this.profileImageUrl = url;
    }
    /**
     * 아티스트를 찜한다
     * @param artistZzim
     */
    public void zzimArtist(ArtistZzim artistZzim) {
        artistZzims.add(artistZzim);
        artistZzim.setUser(this);
    }
    /**
     * 공연을 찜한다
     * @param performanceZzim
     */
    public void zzimPerformance(PerformanceZzim performanceZzim) {
        performanceZzims.add(performanceZzim);
        performanceZzim.setUser(this);
    }
    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    @Builder
    public User(Long id, Long kakaoId, String naverId, String email, String password, String profileImageUrl, String nickname, Gender gender, AgeRange ageRange, UserRole auth, SignupType signupType, String termsAgree, LocalDateTime createdDate, LocalDateTime updateDate, List<PerformanceZzim> performanceZzims, List<ArtistZzim> artistZzims) {
        this.id = id;
        this.kakaoId = kakaoId;
        this.naverId = naverId;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
        this.gender = gender;
        this.ageRange = ageRange;
        this.auth = auth;
        this.signupType = signupType;
        this.termsAgree = termsAgree;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.performanceZzims = performanceZzims;
        this.artistZzims = artistZzims;
    }
}

package com.arton.backend.user.domain;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.zzim.domain.ArtistZzim;
import com.arton.backend.zzim.domain.PerformanceZzim;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Enumerated(EnumType.STRING)
    private Gender gender;
    /** 연령대 */
    @Enumerated(EnumType.STRING)
    private AgeRange ageRange;
    /** 권한 */
    @Enumerated(EnumType.STRING)
    private UserRole auth;
    /** 회원가입 수단 */
    @Enumerated(EnumType.STRING)
    private SignupType signupType;
    /** 마케팅 동의 */
    @Column(length = 1)
    private String termsAgree;
    /** 찜 목록 유저는 찜을 여러개 할 수 있음*/
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    List<PerformanceZzim> performanceZzims = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
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
}

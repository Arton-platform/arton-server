package com.arton.backend.user.adapter.out.repository;

import com.arton.backend.image.adapter.out.repository.UserImageEntity;
import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.user.domain.AgeRange;
import com.arton.backend.user.domain.Gender;
import com.arton.backend.user.domain.SignupType;
import com.arton.backend.user.domain.UserRole;
import com.arton.backend.zzim.adapter.out.repository.ArtistZzimEntity;
import com.arton.backend.zzim.adapter.out.repository.PerformanceZzimEntity;
import com.arton.backend.zzim.domain.ArtistZzim;
import com.arton.backend.zzim.domain.PerformanceZzim;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {
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
    List<PerformanceZzimEntity> performanceZzims = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    List<ArtistZzimEntity> artistZzims = new ArrayList<>();

    private Boolean alertState;

    private String selfDescription;

    /**
     * 아티스트를 찜한다
     * @param artistZzim
     */
    public void zzimArtist(ArtistZzimEntity artistZzim) {
        artistZzims.add(artistZzim);
        artistZzim.setUser(this);
    }
    /**
     * 공연을 찜한다
     * @param performanceZzim
     */
    public void zzimPerformance(PerformanceZzimEntity performanceZzim) {
        performanceZzims.add(performanceZzim);
        performanceZzim.setUser(this);
    }
    public void setPassword(String newPassword){
        this.password = newPassword;
    }
    public void changeAlertState(Boolean state){
        this.alertState = state;
    }
    public void setSelfDescription(String selfDescription) {
        this.selfDescription = selfDescription;
    }

    @Builder
    public UserEntity(Long id, Long kakaoId, String naverId, String email, String password, String nickname, Gender gender, AgeRange ageRange, UserRole auth, SignupType signupType, String termsAgree, List<PerformanceZzimEntity> performanceZzims, List<ArtistZzimEntity> artistZzims, LocalDateTime createdDate, LocalDateTime updatedDate, String selfDescription) {
        super(createdDate, updatedDate);
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
        this.performanceZzims = performanceZzims;
        this.artistZzims = artistZzims;
        this.alertState = true;
        this.selfDescription = selfDescription;
    }
}

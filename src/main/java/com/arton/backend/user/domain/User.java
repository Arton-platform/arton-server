package com.arton.backend.user.domain;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.zzim.adapter.out.repository.ArtistZzimEntity;
import com.arton.backend.zzim.adapter.out.repository.PerformanceZzimEntity;
import com.arton.backend.zzim.domain.ArtistZzim;
import com.arton.backend.zzim.domain.PerformanceZzim;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {
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
    /** 찜 목록 유저는 찜을 여러개 할 수 있음*/
    @ToString.Exclude
    List<PerformanceZzimEntity> performanceZzims = new ArrayList<>();
    @ToString.Exclude
    List<ArtistZzimEntity> artistZzims = new ArrayList<>();
}

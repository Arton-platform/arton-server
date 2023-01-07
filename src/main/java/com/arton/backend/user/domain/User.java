package com.arton.backend.user.domain;

import com.arton.backend.infra.shared.BaseEntity;
import lombok.*;

import javax.persistence.*;

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
    private String marketingAgreement;
}

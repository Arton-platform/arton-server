package com.arton.backend.artist.domain;

import com.arton.backend.performance.domain.Performance;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@ToString
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 아티스트 이름 */
    private String name;
    /** 나이 */
    private Integer age;
    /** sns id */
    private String snsId;
    /** 아티스트의 musical or concert 작품 */
    @OneToMany(mappedBy = "artist")
    @ToString.Exclude
    private List<Performance> performances = new ArrayList<>();

}

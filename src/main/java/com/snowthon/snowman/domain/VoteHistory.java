package com.snowthon.snowman.domain;

import com.snowthon.snowman.dto.type.ESky;
import com.snowthon.snowman.dto.type.wear.EHeadWear;
import com.snowthon.snowman.dto.type.wear.ENeckWear;
import com.snowthon.snowman.dto.type.wear.EOuterWear;
import com.snowthon.snowman.dto.type.wear.ETopWear;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "vote_histories")
public class VoteHistory {

    /**
     * 유저가 투표했을때의 데이터를 저장하는 테이블
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="nickname", nullable = false)
    private String nickname;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="region_id", nullable = false)
    private Region region;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "code", nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "top_wear", nullable = false)
    private ETopWear topWear;

    @Enumerated(EnumType.STRING)
    @Column(name = "outer_wear", nullable = false)
    private EOuterWear outerWear;

    @Enumerated(EnumType.STRING)
    @Column(name = "head_wear")
    private EHeadWear headWear;

    @Enumerated(EnumType.STRING)
    @Column(name = "neck_wear")
    private ENeckWear neckWear;

    @Enumerated(EnumType.STRING)
    @Column(name = "sky")
    private ESky sky;

    @Column(name = "temperature", nullable = false)
    private int temperature;

    @Column(name ="vote_time", nullable = false)
    private LocalDateTime voteTime;

    @Builder
    public VoteHistory(Region region,String nickname, String location, String code, ETopWear topWear, EOuterWear outerWear, EHeadWear headWear, ENeckWear neckWear, ESky sky, int temperature, LocalDateTime voteTime) {
        this.region = region;
        this.nickname = nickname;
        this.location = location;
        this.code = code;
        this.topWear = topWear;
        this.outerWear = outerWear;
        this.headWear = headWear;
        this.neckWear = neckWear;
        this.sky = sky;
        this.temperature = temperature;
        this.voteTime = voteTime;
    }

    //투표를 저장할 때 사용
    public static VoteHistory createFrom(Region region, String nickname, Branch mainBranch, ETopWear topWear, EOuterWear outerWear, EHeadWear headWear, ENeckWear neckWear) {
        return VoteHistory.builder()
                .region(region)
                .nickname(nickname)
                .location(region.getLocation())
                .code(region.getCode())
                .temperature(mainBranch.getTemperature())
                .outerWear(outerWear)
                .topWear(topWear)
                .headWear(headWear)
                .neckWear(neckWear)
                .sky(mainBranch.getSky())
                .voteTime(LocalDateTime.now())
                .build();
    }

}

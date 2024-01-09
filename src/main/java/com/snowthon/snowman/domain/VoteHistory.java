package com.snowthon.snowman.domain;

import com.snowthon.snowman.dto.request.VoteRequestDto;
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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "code", nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "top_wear", nullable = false)
    private ETopWear topWear;

    @Enumerated(EnumType.STRING)
    @Column(name = "`outer`", nullable = false)
    private EOuterWear outer;

    @Enumerated(EnumType.STRING)
    @Column(name = "head_wear")
    private EHeadWear headWear;

    @Enumerated(EnumType.STRING)
    @Column(name = "neck_wear")
    private ENeckWear neckWear;

    @Column(name = "temperature", nullable = false)
    private int temperature;

    @Column(name ="vote_time", nullable = false)
    private LocalDateTime voteTime;

    @Builder
    public VoteHistory(User user, String location, String code, ETopWear topWear, EOuterWear outer, EHeadWear headWear, ENeckWear neckWear, int temperature, LocalDateTime voteTime) {
        this.user = user;
        this.location = location;
        this.code = code;
        this.topWear = topWear;
        this.outer = outer;
        this.headWear = headWear;
        this.neckWear = neckWear;
        this.temperature = temperature;
        this.voteTime = voteTime;
    }

    //투표를 저장할 때 사용
    public static VoteHistory createFrom(User user, Region region, Branch mainBranch, VoteRequestDto requestDto) {
        return VoteHistory.builder()
                .user(user)
                .location(region.getLocation())
                .code(region.getCode())
                .temperature(mainBranch.getTemperature())
                .topWear(requestDto.getTopWear())
                .outer(requestDto.getOuter())
                .headWear(requestDto.getHeadWear())
                .neckWear(requestDto.getNeckWear())
                .voteTime(LocalDateTime.now())
                .build();
    }

}

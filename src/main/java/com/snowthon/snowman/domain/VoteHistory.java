package com.snowthon.snowman.domain;

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

    @Column(name = "highest_temperature", nullable = false)
    private int highestTemperature;

    @Column(name = "lowest_temperature", nullable = false)
    private int lowestTemperature;


    @Column(name ="vote_time", nullable = false)
    private LocalDateTime voteTime;

    @Builder
    public VoteHistory(User user, ETopWear topWear, EOuterWear outer, EHeadWear headWear, ENeckWear neckWear, int highestTemperature, int lowestTemperature, LocalDateTime voteTime) {
        this.user = user;
        this.topWear = topWear;
        this.outer = outer;
        this.headWear = headWear;
        this.neckWear = neckWear;
        this.highestTemperature = highestTemperature;
        this.lowestTemperature = lowestTemperature;
        this.voteTime = voteTime;
    }
}

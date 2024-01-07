package com.snowthon.snowman.domain;

import com.snowthon.snowman.domain.wear.HeadWear;
import com.snowthon.snowman.domain.wear.NeckWear;
import com.snowthon.snowman.domain.wear.OuterWear;
import com.snowthon.snowman.domain.wear.TopWear;
import com.snowthon.snowman.dto.type.EBranchType;
import com.snowthon.snowman.dto.type.wear.EHeadWear;
import com.snowthon.snowman.dto.type.wear.ENeckWear;
import com.snowthon.snowman.dto.type.wear.EOuterWear;
import com.snowthon.snowman.dto.type.wear.ETopWear;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "branch")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outer_wear_id")
    private OuterWear outerWear;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "top_wear_id")
    private TopWear topWear;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "neck_wear_id")
    private NeckWear neckWear;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_wear_id")
    private HeadWear headWear;

    /**
     * highestTemperature, lowestTemperature
     *
     * mainBranch일 경우 최고, 최저 null
     * 다른 브랜치의 경우 필수
     */
    @Column(name = "lowest_temperature")
    private Integer highestTemperature;

    @Column(name = "highest_temperature")
    private Integer lowestTemperature;

    @Column(name = "branch_time", nullable = false)
    private String branchTime; // ex) 1월 3일 오후

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weather_id", nullable = false)
    private Weather weather;

    @Column(name = "branch_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EBranchType branchType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Branch(Integer highestTemperature, Integer lowestTemperature, String branchTime, Weather weather, EBranchType branchType) {
        this.highestTemperature = highestTemperature;
        this.lowestTemperature = lowestTemperature;
        this.branchTime = branchTime;
        this.weather = weather;
        this.branchType = branchType;
        this.createdAt = LocalDateTime.now();
    }
    /**
     * TODO : 브랜치 생성 로직
     */

}

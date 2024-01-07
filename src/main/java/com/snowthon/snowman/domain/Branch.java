package com.snowthon.snowman.domain;

import com.snowthon.snowman.dto.type.EBranchType;
import com.snowthon.snowman.dto.type.wear.EHeadWear;
import com.snowthon.snowman.dto.type.wear.ENeckWear;
import com.snowthon.snowman.dto.type.wear.EOuter;
import com.snowthon.snowman.dto.type.wear.ETopWear;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "branch")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * outer, topwear는 필수
     * neckwear, headwear는 선택
     */
    @Column(name = "`outer`", nullable = false)
    @Enumerated(EnumType.STRING)
    private EOuter outer;

    @Column(name = "top_wear", nullable = false)
    @Enumerated(EnumType.STRING)
    private ETopWear topWear;

    @Column(name = "neck_wear")
    @Enumerated(EnumType.STRING)
    private ENeckWear neckWear;

    @Column(name = "head_wear")
    @Enumerated(EnumType.STRING)
    private EHeadWear headWear;

    /**
     * mainBranch일 경우 최고, 최저 필수
     * 다른 브랜치의 경우 null로 처리
     */

    @Column(name = "lowest_temperature")
    private Integer highestTemperature;

    @Column(name = "highest_temperature")
    private Integer lowestTemperature;

    @Column(name = "branch_time", nullable = false)
    private String branchTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weather_id", nullable = false)
    private Weather weather;

    @Column(name = "branch_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EBranchType branchType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;



    @Builder
    public Branch(EOuter outer, ETopWear topWear, ENeckWear neckWear, EHeadWear headWear, Integer highestTemperature, Integer lowestTemperature, String branchTime, Weather weather, EBranchType branchType) {
        this.outer = outer;
        this.topWear = topWear;
        this.neckWear = neckWear;
        this.headWear = headWear;
        this.highestTemperature = highestTemperature;
        this.lowestTemperature = lowestTemperature;
        this.branchTime = branchTime;
        this.weather = weather;
        this.branchType = branchType;
        this.createdAt = LocalDateTime.now();
    }
}

package com.snowthon.snowman.domain.wear;

import com.snowthon.snowman.dto.type.ELevel;
import com.snowthon.snowman.dto.type.wear.ETopWear;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "top_wear")
public class TopWear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "neat_cnt", nullable = false)
    private int neatCnt;

    @Column(name = "short_sleeve_cnt", nullable = false)
    private int longSleeveCnt;

    @Enumerated(EnumType.STRING)
    private ETopWear eTopWear;

    @Builder
    public TopWear(int neatCnt, int longSleeveCnt, ELevel level) {
        this.neatCnt = neatCnt;
        this.longSleeveCnt = longSleeveCnt;
        if (level == ELevel.LEVEL_4 || level == ELevel.LEVEL_6 || level == ELevel.LEVEL_8) {
            this.eTopWear = ETopWear.LONG_SLEEVE;
        } else {
            this.eTopWear = ETopWear.NEAT;
        }
    }

    public static TopWear createTopWear(int neatCnt, int longSleeveCnt, ELevel level) {
        return TopWear.builder()
                .neatCnt(neatCnt)
                .longSleeveCnt(longSleeveCnt)
                .level(level)
                .build();
    }

    /**
     * 투표했을때 업데이트
     */
    public void updateVote(ETopWear topWear) {
        if (topWear == ETopWear.NEAT) {
            this.neatCnt++;
        } else if (topWear == ETopWear.LONG_SLEEVE) {
            this.longSleeveCnt++;
        }
        double totalVotes = neatCnt + longSleeveCnt;
        double neatScore = (totalVotes > 0) ? (neatCnt / totalVotes) * 0.5 : 0;
        double longSleeveScore = (totalVotes > 0) ? (longSleeveCnt / totalVotes) * 0.5 : 0;

        if (this.eTopWear == ETopWear.NEAT) {
            neatScore += 0.5;
        }else{
            longSleeveScore += 0.5;
        }
        this.eTopWear = longSleeveScore > neatScore ? ETopWear.LONG_SLEEVE : ETopWear.NEAT;
    }
}

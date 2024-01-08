package com.snowthon.snowman.domain.wear;

import com.snowthon.snowman.dto.type.ELevel;
import com.snowthon.snowman.dto.type.wear.EHeadWear;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "head_wear")
public class HeadWear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "beanie_cnt", nullable = false)
    private int earmuffCnt;

    @Column(name = "balaclava_cnt", nullable = false)
    private int balaclavaCnt;

    @Column(name = "e_head_wear")
    @Enumerated(EnumType.STRING)
    private EHeadWear eheadWear;

    @Builder
    public HeadWear(int earmuffCnt, int balaclavaCnt, ELevel level) {
        if (level == ELevel.LEVEL_1) {
            this.eheadWear = EHeadWear.BALACLAVA;
        } else if (level == ELevel.LEVEL_2 || level == ELevel.LEVEL_3) {
            this.eheadWear = EHeadWear.EAR_MUFFS;
        } else {
            this.eheadWear = EHeadWear.NONE;
        }
        this.earmuffCnt = earmuffCnt;
        this.balaclavaCnt = balaclavaCnt;
    }

    public void updateVote(EHeadWear headWear) {
        if (headWear == EHeadWear.EAR_MUFFS) {
            this.earmuffCnt++;
        } else if (headWear == EHeadWear.BALACLAVA) {
            this.balaclavaCnt++;
        }
        double totalVotes = earmuffCnt + balaclavaCnt;
        double earmuffScore = (totalVotes > 0) ? (earmuffCnt / totalVotes) * 0.5 : 0;
        double balaclavaScore = (totalVotes > 0) ? (balaclavaCnt / totalVotes) * 0.5 : 0;
        if (this.eheadWear == EHeadWear.EAR_MUFFS) {
            earmuffScore += 0.5;
        } else if (this.eheadWear == EHeadWear.BALACLAVA) {
            balaclavaScore += 0.5;
        } else if (this.eheadWear == EHeadWear.NONE) {
            return;
        }
        this.eheadWear = earmuffScore > balaclavaScore ? EHeadWear.EAR_MUFFS : EHeadWear.BALACLAVA;
    }

}

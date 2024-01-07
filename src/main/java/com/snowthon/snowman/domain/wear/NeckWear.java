package com.snowthon.snowman.domain.wear;

import com.snowthon.snowman.dto.type.ELevel;
import com.snowthon.snowman.dto.type.wear.ENeckWear;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "neck_wear_cnt")
public class NeckWear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "scarf_cnt", nullable = false)
    private int scarfCnt;

    @Column(name = "e_neck_wear")
    private ENeckWear eNeckWear;



    @Builder
    public NeckWear(int scarfCnt, ELevel level) {
        if (level == ELevel.LEVEL_1 || level == ELevel.LEVEL_2 || level == ELevel.LEVEL_3) {
            this.eNeckWear = ENeckWear.SCARF;
        } else {
            this.eNeckWear = ENeckWear.NONE;
        }
        this.scarfCnt = scarfCnt;
    }

    public void updateVote(ENeckWear neckWear) {
        if (neckWear == ENeckWear.SCARF) {
            this.scarfCnt++;
        }
        double totalVotes = scarfCnt;
        double scarfScore = (totalVotes > 0) ? (scarfCnt / totalVotes) * 0.5 : 0;
        if (this.eNeckWear == ENeckWear.SCARF) {
            scarfScore += 0.5;
        } else if (this.eNeckWear == ENeckWear.NONE) {
            return;
        }
        this.eNeckWear = scarfScore > 0.5 ? ENeckWear.SCARF : ENeckWear.NONE;
    }
}

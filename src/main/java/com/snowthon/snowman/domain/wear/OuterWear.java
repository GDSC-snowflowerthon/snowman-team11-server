package com.snowthon.snowman.domain.wear;

import com.snowthon.snowman.dto.type.ELevel;
import com.snowthon.snowman.dto.type.wear.EOuterWear;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "outer_wear_cnt")
public class OuterWear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "long_padding_cnt", nullable = false)
    private int longPaddingCnt;

    @Column(name = "short_padding_cnt", nullable = false)
    private int shortPaddingCnt;

    @Column(name = "coat_cnt", nullable = false)
    private int coatCnt;

    @Column(name = "e_outer_wear")
    @Enumerated(EnumType.STRING)
    private EOuterWear eOuterWear;

    @Builder
    public OuterWear(int longPaddingCnt, int shortPaddingCnt, int coatCnt, ELevel level) {

        if(level == ELevel.LEVEL_1 || level == ELevel.LEVEL_2){
            this.eOuterWear = EOuterWear.LONG_PADDING;
        } else if (level == ELevel.LEVEL_3 || level == ELevel.LEVEL_4) {
            this.eOuterWear = EOuterWear.SHORT_PADDING;
        } else if (level == ELevel.LEVEL_5 || level == ELevel.LEVEL_6) {
            this.eOuterWear = EOuterWear.COAT;
        } else{
            this.eOuterWear = EOuterWear.NONE;
        }
        this.longPaddingCnt = longPaddingCnt;
        this.shortPaddingCnt = shortPaddingCnt;
        this.coatCnt = coatCnt;
    }

    public void updateVote(EOuterWear outerWear) {
        if (outerWear == EOuterWear.LONG_PADDING) {
            this.longPaddingCnt++;
        } else if (outerWear == EOuterWear.SHORT_PADDING) {
            this.shortPaddingCnt++;
        } else if (outerWear == EOuterWear.COAT) {
            this.coatCnt++;
        }
        double totalVotes = longPaddingCnt + shortPaddingCnt + coatCnt;
        double longPaddingScore = (totalVotes > 0) ? (longPaddingCnt / totalVotes) * 0.33 : 0;
        double shortPaddingScore = (totalVotes > 0) ? (shortPaddingCnt / totalVotes) * 0.33 : 0;
        double coatScore = (totalVotes > 0) ? (coatCnt / totalVotes) * 0.33 : 0;
        if(this.eOuterWear == EOuterWear.LONG_PADDING){
            longPaddingScore += 0.33;
        } else if(this.eOuterWear == EOuterWear.SHORT_PADDING){
            shortPaddingScore += 0.33;
        } else if (this.eOuterWear == EOuterWear.COAT) {
            coatScore += 0.33;
        } else if(this.eOuterWear == EOuterWear.NONE){
            return;
        }
        this.eOuterWear = (longPaddingScore > shortPaddingScore) ? (longPaddingScore > coatScore ? EOuterWear.LONG_PADDING : EOuterWear.COAT) : (shortPaddingScore > coatScore ? EOuterWear.SHORT_PADDING : EOuterWear.COAT);
    }
}

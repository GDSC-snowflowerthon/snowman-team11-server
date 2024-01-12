package com.snowthon.snowman.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.snowthon.snowman.domain.Branch;
import com.snowthon.snowman.domain.Region;
import com.snowthon.snowman.domain.wear.HeadWear;
import com.snowthon.snowman.domain.wear.NeckWear;
import com.snowthon.snowman.domain.wear.OuterWear;
import com.snowthon.snowman.domain.wear.TopWear;
import com.snowthon.snowman.dto.type.wear.EHeadWear;
import com.snowthon.snowman.dto.type.wear.ENeckWear;
import com.snowthon.snowman.dto.type.wear.EOuterWear;
import com.snowthon.snowman.dto.type.wear.ETopWear;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class VoteRateDto {


    @JsonProperty("location") @Schema(description = "위치", example = "서울특별시 중구 명동")
    @NotNull(message = "location는 필수입니다.")
    private String location;

    @JsonProperty("headWear") @Schema(description = "모자 정보")
    @NotNull(message = "location는 필수입니다.")
    private HeadWearDto headWear;

    @JsonProperty("neckWear") @Schema(description = "목도리 정보")
    @NotNull(message = "location는 필수입니다.")
    private NeckWearDto neckWear;

    @JsonProperty("outerWear") @Schema(description = "아우터 정보")
    @NotNull(message = "location는 필수입니다.")
    private OuterWearDto outerWear;

    @JsonProperty("topWear") @Schema(description = "상의 정보")
    @NotNull(message = "location는 필수입니다.")
    private TopWearDto topWear;

    @Getter
    @NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
    public static class HeadWearDto {

        @JsonProperty("headWear") @Schema(description = "모자 정보", example = "EAR_MUFFS")
        @NotNull(message = "headWear는 필수입니다.")
        private EHeadWear eHeadWear;

        @JsonProperty("earmuffCnt") @Schema(description = "귀마개 받은 투표 개수 ", example = "1")
        @NotNull(message = "earmuffCnt는 필수입니다.")
        private int earmuffCnt;

        @JsonProperty("balaclavaCnt") @Schema(description = "바클라바 받은 투표 개수 ", example = "1")
        @NotNull(message = "balaclavaCnt는 필수입니다.")
        private int balaclavaCnt;

        @Builder
        private HeadWearDto( EHeadWear eHeadWear, int earmuffCnt, int balaclavaCnt) {
            this.earmuffCnt = earmuffCnt;
            this.balaclavaCnt = balaclavaCnt;
            this.eHeadWear = eHeadWear;
        }

        public static HeadWearDto fromEntity(HeadWear headWear) {
            return HeadWearDto.builder()
                    .eHeadWear(headWear.getEheadWear())
                    .earmuffCnt(headWear.getEarmuffCnt())
                    .balaclavaCnt(headWear.getBalaclavaCnt())
                    .build();
        }

    }

    @Getter
    @NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
    public static class NeckWearDto {

        @JsonProperty("neckWear") @Schema(description = "목도리 정보", example = "SCARF")
        @NotNull(message = "neckWear는 필수입니다.")
        private ENeckWear eNeckWear;

        @JsonProperty("scarfCnt") @Schema(description = "스카프 받은 투표 개수 ", example = "1")
        @NotNull(message = "scarfCnt는 필수입니다.")
        private int scarfCnt;


        @Builder
        private NeckWearDto( ENeckWear eNeckWear, int scarfCnt) {
            this.eNeckWear = eNeckWear;
            this.scarfCnt = scarfCnt;
        }

        public static NeckWearDto fromEntity(NeckWear neckWear) {
            return NeckWearDto.builder()
                    .eNeckWear(neckWear.getENeckWear())
                    .scarfCnt(neckWear.getScarfCnt())
                    .build();
        }

    }

    @Getter
    @NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
    public static class OuterWearDto {

        @JsonProperty("outerWear") @Schema(description = "아우터 정보", example = "COAT")
        @NotNull(message = "outerWear는 필수입니다.")
        private EOuterWear outerWear;

        @JsonProperty("longPaddingCnt") @Schema(description = "롱패딩 받은 투표 개수 ", example = "1")
        @NotNull(message = "longPaddingCnt는 필수입니다.")
        private int longPaddingCnt;

        @JsonProperty("shortPaddingCnt") @Schema(description = "숏패딩 받은 투표 개수 ", example = "1")
        @NotNull(message = "shortPaddingCnt는 필수입니다.")
        private int shortPaddingCnt;

        @JsonProperty("coatCnt") @Schema(description = "코트 받은 투표 개수 ", example = "1")
        @NotNull(message = "coatCnt 필수입니다.")
        private int coatCnt;


        @Builder
        private OuterWearDto(EOuterWear outerWear, int longPaddingCnt, int shortPaddingCnt, int coatCnt) {
            this.outerWear = outerWear;
            this.longPaddingCnt = longPaddingCnt;
            this.shortPaddingCnt = shortPaddingCnt;
            this.coatCnt = coatCnt;
        }

        public static OuterWearDto fromEntity(OuterWear outerWear) {
            return OuterWearDto.builder()
                    .outerWear(outerWear.getEOuterWear())
                    .longPaddingCnt(outerWear.getLongPaddingCnt())
                    .shortPaddingCnt(outerWear.getShortPaddingCnt())
                    .coatCnt(outerWear.getCoatCnt())
                    .build();
        }

    }

    @Getter
    @NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class TopWearDto {

        @JsonProperty("topWear") @Schema(description = "상의 정보", example = "LONG_SLEEVE")
        @NotNull(message = "topWear는 필수입니다.")
        private ETopWear topWear;

        @JsonProperty("neatCnt") @Schema(description = "니트 받은 투표 개수 ", example = "1")
        @NotNull(message = "neatCnt는 필수입니다.")
        private int neatCnt;

        @JsonProperty("longSleeveCnt") @Schema(description = "긴팔 받은 투표 개수 ", example = "1")
        @NotNull(message = "longSleeveCnt는 필수입니다.")
        private int longSleeveCnt;

        @Builder
        private TopWearDto( ETopWear topWear, int neatCnt, int longSleeveCnt) {
            this.topWear = topWear;
            this.neatCnt = neatCnt;
            this.longSleeveCnt = longSleeveCnt;
        }

        public static TopWearDto formEntity(TopWear topWear) {
            return TopWearDto.builder()
                    .topWear(topWear.getETopWear())
                    .neatCnt(topWear.getNeatCnt())
                    .longSleeveCnt(topWear.getLongSleeveCnt())
                    .build();
        }
    }

    @Builder
    public VoteRateDto(String location, HeadWearDto headWear, NeckWearDto neckWear, OuterWearDto outerWear, TopWearDto topWear) {
        this.location = location;
        this.headWear = headWear;
        this.neckWear = neckWear;
        this.outerWear = outerWear;
        this.topWear = topWear;
    }

    public static VoteRateDto fromEntity(Region region) {
        Branch mainBranch = region.getMainBranch();
        return VoteRateDto.builder()
                .location(region.getLocation())
                .headWear(HeadWearDto.fromEntity(mainBranch.getHeadWear()))
                .neckWear(NeckWearDto.fromEntity(mainBranch.getNeckWear()))
                .outerWear(OuterWearDto.fromEntity(mainBranch.getOuterWear()))
                .topWear(TopWearDto.formEntity(mainBranch.getTopWear()))
                .build();
    }
}

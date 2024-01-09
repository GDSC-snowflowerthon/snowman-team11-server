package com.snowthon.snowman.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.snowthon.snowman.domain.Branch;
import com.snowthon.snowman.domain.Region;
import com.snowthon.snowman.dto.type.ESky;
import com.snowthon.snowman.dto.type.wear.Wear;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record WeatherInfoDto(

        @NotNull(message = "날씨 ID는 필수입니다.")
        @JsonProperty("weatherId") @Schema(description = "날씨 ID", example = "1")
        Long weatherId,

        @NotNull(message = "위치는 필수입니다.")
        @JsonProperty("location") @Schema(description = "위치", example = "서울특별시 중구 명동")
        String location,

        @NotNull(message = "온도는 필수입니다.")
        @JsonProperty("temperature") @Schema(description = "온도", example = "-15")
        int temperature,

        @NotNull(message = "날씨 상태는 필수입니다.")
        @JsonProperty("weatherStatus") @Schema(description = "날씨 상태", example = "SNOW")
        ESky weatherStatus,

        @JsonProperty("mainBranch") @Schema(description = "메인 분기")
        @NotNull(message = "메인 분기는 필수입니다.")
        BranchInfo mainBranch,

        @NotNull(message = "첫 번째 분기는 필수입니다.")
        @JsonProperty("firstBranch") @Schema(description = "첫 번째 분기")
        BranchInfo firstBranch,

        @NotNull(message = "두 번째 분기는 필수입니다.")
        @JsonProperty("secondBranch") @Schema(description = "두 번째 분기")
        BranchInfo secondBranch,

        @NotNull(message = "세 번째 분기는 필수입니다.")
        @JsonProperty("thirdBranch") @Schema(description = "세 번째 분기")
        BranchInfo thirdBranch
) {
    @Builder
    public record BranchInfo(
            @NotNull(message = "상의 정보는 필수입니다.")
            @JsonProperty("topWear") @Schema(description = "상의 정보", example = "LONG_SLEEVE")
            Wear topWear,

            @NotNull(message = "목도리 정보는 필수입니다.")
            @JsonProperty("neckWear") @Schema(description = "목도리 정보", example = "SCARF")
            Wear neckWear,

            @NotNull(message = "아우터 정보는 필수입니다.")
            @JsonProperty("outerWear") @Schema(description = "아우터 정보", example = "COAT")
            Wear outerWear,

            @NotNull(message = "머리 정보는 필수입니다.")
            @JsonProperty("headWear") @Schema(description = "머리 정보", example = "BALACLAVA")
            Wear headWear,

            @JsonProperty("highestTemperature") @Schema(description = "최고 기온", example = "10")
            Integer highestTemperature,

            @JsonProperty("lowestTemperature") @Schema(description = "최저 기온", example = "-10")
            Integer lowestTemperature,

            @JsonProperty("branchTime") @Schema(description = "분기 시간", example = "1월 3일 오후")
            String branchTime
    ) {

            public static BranchInfo fromEntity(Branch branch) {
                return BranchInfo.builder()
                        .topWear(branch.getTopWear().getETopWear())
                        .neckWear(branch.getNeckWear().getENeckWear())
                        .outerWear(branch.getOuterWear().getEOuterWear())
                        .headWear(branch.getHeadWear().getEheadWear())
                        .highestTemperature(branch.getHighestTemperature())
                        .lowestTemperature(branch.getLowestTemperature())
                        .branchTime(branch.getBranchTime())
                        .build();
            }
    }

    public static WeatherInfoDto fromEntity(Region region) {
        return WeatherInfoDto.builder()
                .weatherId(region.getId())
                .location(region.getLocation())
                .temperature(region.getMainBranch().getTemperature())
                .weatherStatus(region.getMainBranch().getSky())
                .mainBranch(BranchInfo.fromEntity(region.getMainBranch()))
                .firstBranch(BranchInfo.fromEntity(region.getFirstBranch()))
                .secondBranch(BranchInfo.fromEntity(region.getSecondBranch()))
                .thirdBranch(BranchInfo.fromEntity(region.getThirdBranch()))
                .build();
    }

}

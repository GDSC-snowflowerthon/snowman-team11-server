package com.snowthon.snowman.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.snowthon.snowman.domain.Branch;
import com.snowthon.snowman.domain.Weather;
import com.snowthon.snowman.dto.type.ESky;
import com.snowthon.snowman.dto.type.wear.Wear;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record WeatherInfoDto(

        @NotNull(message = "날씨 ID는 필수입니다.")
        @JsonProperty("weather_id") @Schema(description = "날씨 ID", example = "1")
        Long weatherId,

        @NotNull(message = "위치는 필수입니다.")
        @JsonProperty("location") @Schema(description = "위치", example = "서울특별시 중구 명동")
        String location,

        @NotNull(message = "온도는 필수입니다.")
        @JsonProperty("temperature") @Schema(description = "온도", example = "-15")
        int temperature,

        @NotNull(message = "날씨 상태는 필수입니다.")
        @JsonProperty("weather_status") @Schema(description = "날씨 상태", example = "SNOW")
        ESky weatherStatus,

        @JsonProperty("main_branch") @Schema(description = "메인 분기")
        @NotNull(message = "메인 분기는 필수입니다.")
        BranchInfo mainBranch,

        @NotNull(message = "첫 번째 분기는 필수입니다.")
        @JsonProperty("first_branch") @Schema(description = "첫 번째 분기")
        BranchInfo firstBranch,

        @NotNull(message = "두 번째 분기는 필수입니다.")
        @JsonProperty("second_branch") @Schema(description = "두 번째 분기")
        BranchInfo secondBranch,

        @NotNull(message = "세 번째 분기는 필수입니다.")
        @JsonProperty("third_branch") @Schema(description = "세 번째 분기")
        BranchInfo thirdBranch
) {
    @Builder
    public record BranchInfo(
            @NotNull(message = "상의 정보는 필수입니다.")
            @JsonProperty("top_wear") @Schema(description = "상의 정보", example = "LONG_SLEEVE")
            Wear topWear,

            @NotNull(message = "목도리 정보는 필수입니다.")
            @JsonProperty("neck_wear") @Schema(description = "목도리 정보", example = "SCARF")
            Wear neckWear,

            @NotNull(message = "아우터 정보는 필수입니다.")
            @JsonProperty("outer_wear") @Schema(description = "아우터 정보", example = "COAT")
            Wear outerWear,

            @NotNull(message = "머리 정보는 필수입니다.")
            @JsonProperty("head_wear") @Schema(description = "머리 정보", example = "BALACLAVA")
            Wear headWear,

            @JsonProperty("highest_temperature") @Schema(description = "최고 기온", example = "10")
            Integer highestTemperature,

            @JsonProperty("lowest_temperature") @Schema(description = "최저 기온", example = "-10")
            Integer lowestTemperature,

            @JsonProperty("branch_time") @Schema(description = "분기 시간", example = "1월 3일 오후")
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

    public static WeatherInfoDto fromEntity(Weather weather) {
        return WeatherInfoDto.builder()
                .weatherId(weather.getId())
                .location(weather.getLocation())
                .temperature(weather.getTemperature())
                .weatherStatus(weather.getSky())
                .mainBranch(BranchInfo.fromEntity(weather.getMainBranch()))
                .firstBranch(BranchInfo.fromEntity(weather.getFirstBranch()))
                .secondBranch(BranchInfo.fromEntity(weather.getSecondBranch()))
                .thirdBranch(BranchInfo.fromEntity(weather.getThirdBranch()))
                .build();
    }

}

package com.snowthon.snowman.dto.request;

import com.snowthon.snowman.dto.type.ESky;
import com.snowthon.snowman.dto.type.wear.Wear;
import lombok.Builder;

@Builder
public record WeatherInfoDto(
        Long weatherId,
        String location,
        int temperature,
        ESky weatherStatus,
        BranchInfo mainBranch,
        BranchInfo firstBranch,
        BranchInfo secondBranch,
        BranchInfo thirdBranch
) {
    @Builder
    public static record BranchInfo(
            Wear topWear,
            Wear neckWear,
            Wear outerWear,
            Wear headWear,
            Integer highestTemperature,
            Integer lowestTemperature,
            String branchTime
    ) {
    }
}

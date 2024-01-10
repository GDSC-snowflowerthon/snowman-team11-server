package com.snowthon.snowman.dto.response;

import com.snowthon.snowman.dto.type.ESky;
import com.snowthon.snowman.dto.type.wear.EHeadWear;
import com.snowthon.snowman.dto.type.wear.ENeckWear;
import com.snowthon.snowman.dto.type.wear.EOuterWear;
import com.snowthon.snowman.dto.type.wear.ETopWear;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AchivingDetailDto(
        @NotNull(message = "이름은 필수입니다.")
        Long id,
        @NotNull(message = "location은 필수입니다.")
        String location,
        @NotNull(message = "topWear은 필수입니다.")
        ETopWear topWear,
        @NotNull(message = "outerWear은 필수입니다.")
        EOuterWear outerWear,
        @NotNull(message = "headWear은 필수입니다.")
        EHeadWear headWear,
        @NotNull(message = "neckWear은 필수입니다.")
        ENeckWear neckWear,

        @NotNull(message = "날씨 정보는 필수입니다.")
        ESky sky,
        @NotNull(message = "temperature은 필수입니다.")
        int temperature,
        @NotNull(message = "voteTime은 필수입니다.")
        LocalDateTime voteTime
) {
}

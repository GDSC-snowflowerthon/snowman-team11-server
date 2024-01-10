package com.snowthon.snowman.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.snowthon.snowman.dto.type.wear.EHeadWear;
import com.snowthon.snowman.dto.type.wear.ENeckWear;
import com.snowthon.snowman.dto.type.wear.EOuterWear;
import com.snowthon.snowman.dto.type.wear.ETopWear;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VoteRequestDto(

        @NotNull(message = "상의 정보는 필수입니다.")
        @JsonProperty("top_wear") @Schema(description = "상의 정보", example = "LONG_SLEEVE")
        ETopWear topWear,

        @NotNull(message = "목도리 정보는 필수입니다.")
        @JsonProperty("neck_wear") @Schema(description = "목도리 정보", example = "SCARF")
        ENeckWear neckWear,

        @NotNull(message = "아우터 정보는 필수입니다.")
        @JsonProperty("outer_wear") @Schema(description = "아우터 정보", example = "COAT")
        EOuterWear outerWear,

        @NotNull(message = "모자 정보는 필수입니다.")
        @JsonProperty("head_wear") @Schema(description = "모자 정보", example = "EAR_MUFFS")
        EHeadWear headWear
) {
}

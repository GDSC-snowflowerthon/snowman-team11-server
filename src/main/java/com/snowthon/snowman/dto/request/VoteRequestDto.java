package com.snowthon.snowman.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VoteRequestDto
        (

        @NotNull(message = "상의 정보는 필수입니다.")
        @JsonProperty("topWear") @Schema(description = "상의 정보")
        String topWear,

        @NotNull(message = "목도리 정보는 필수입니다.")
        @JsonProperty("neckWear") @Schema(description = "목도리 정보")
        String neckWear,

        @NotNull(message = "아우터 정보는 필수입니다.")
        @JsonProperty("outerWear") @Schema(description = "아우터 정보")
        String outerWear,

        @NotNull(message = "모자 정보는 필수입니다.")
        @JsonProperty("headWear") @Schema(description = "모자 정보")
        String headWear
) {
}

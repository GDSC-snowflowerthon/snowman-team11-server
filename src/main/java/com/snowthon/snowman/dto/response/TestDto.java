package com.snowthon.snowman.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
public record TestDto (

        @Schema(description = "이름", example = "홍길동")
        @NotNull(message = "이름은 필수입니다.")
        String name,

        @Schema(description = "나이", example = "20")
        @NotNull(message = "나이는 필수입니다.")
        int age

){
}

package com.snowthon.snowman.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserLoginDto(

        @NotNull(message = "이메일은 빈값이 될 수 없습니다.")
        String email,

        @NotNull(message = "전화번호는 빈값이 될 수 없습니다.")
        String phoneNumber,

        @NotNull(message = "닉네임은 빈 값이 될 수 없습니다.")
        String nickname
) {
}

package com.snowthon.snowman.controller;

import com.snowthon.snowman.contrant.Constants;
import com.snowthon.snowman.dto.common.ResponseDto;
import com.snowthon.snowman.dto.request.UserLoginDto;
import com.snowthon.snowman.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.API_PREFIX + "/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인")
    @Schema(name = "login", description = "로그인")
    public ResponseDto<?> login(@RequestBody UserLoginDto userloginDto) {
        return ResponseDto.ok(authService.login(userloginDto));
    }
}

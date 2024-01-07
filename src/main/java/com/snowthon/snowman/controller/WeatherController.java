package com.snowthon.snowman.controller;

import com.snowthon.snowman.annotation.UserId;
import com.snowthon.snowman.contrant.Constants;
import com.snowthon.snowman.dto.common.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_PREFIX + "/weathers")
public class WeatherController {

    @GetMapping("")
    @Operation(summary = "날씨 정보 조회", description = "날씨 정보를 조회합니다.")
    public ResponseDto<?> showWeatherInfo(
    ) {
        return ResponseDto.ok("weather info");
    }
}

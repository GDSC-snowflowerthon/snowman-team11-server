package com.snowthon.snowman.controller;

import com.snowthon.snowman.contrant.Constants;
import com.snowthon.snowman.dto.common.ResponseDto;
import com.snowthon.snowman.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.API_PREFIX + "/weathers")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("")
    @Operation(summary = "날씨 정보 조회", description = "날씨 정보를 조회합니다.")
    public ResponseDto<?> showWeatherInfo(
            @Param("latitude") @Schema(description = "위도", example = "37.5665") Double latitude,
            @Param("longitude") @Schema(description = "경도", example = "126.9780") Double longitude
    ) {
        return ResponseDto.ok(weatherService.getWeather(latitude, longitude));
    }

}

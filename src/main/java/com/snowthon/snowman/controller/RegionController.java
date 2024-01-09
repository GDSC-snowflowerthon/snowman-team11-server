package com.snowthon.snowman.controller;

import com.snowthon.snowman.annotation.UserId;
import com.snowthon.snowman.contrant.Constants;
import com.snowthon.snowman.dto.common.ResponseDto;
import com.snowthon.snowman.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.API_PREFIX + "/weathers")
public class RegionController {

    private final RegionService regionService;

    //2-1. 날씨 정보 조회
    @GetMapping("")
    @Operation(summary = "날씨 정보 조회", description = "날씨 정보를 조회합니다.")
    public ResponseDto<?> showWeatherInfo(
            @Param("latitude") @Schema(description = "위도", example = "37.5665") Double latitude,
            @Param("longitude") @Schema(description = "경도", example = "126.9780") Double longitude
    ) {
        return ResponseDto.ok(regionService.getWeather(latitude, longitude));
    }


    //3-1 투표 여부 조회
    @GetMapping("/{weatherId}/poll")
    @Operation(summary = "투표 여부 조회", description = "투표 여부를 조회합니다")
    public ResponseDto<?> isVoted(@UserId Long userId) {


        return ResponseDto.ok(null);
    }

    //3-2 투표 하기
    @PostMapping("/{weatherId}/poll")
    @Operation(summary = "투표 하기", description = "투표를 반영합니다")
    public ResponseDto<?> vote(@UserId Long userId) {


        return ResponseDto.ok("투표가 완료되었습니다. ");
    }


}

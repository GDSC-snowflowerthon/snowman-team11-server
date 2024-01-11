package com.snowthon.snowman.controller;

import com.snowthon.snowman.annotation.UserId;
import com.snowthon.snowman.contrant.Constants;
import com.snowthon.snowman.dto.common.ResponseDto;
import com.snowthon.snowman.dto.request.VoteRequestDto;
import com.snowthon.snowman.repository.UserRepository;
import com.snowthon.snowman.service.RegionService;
import com.snowthon.snowman.service.VoteHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(Constants.API_PREFIX + "/weathers")
@Tag(name = "Region", description = "지역 관련 API")
public class RegionController {

    private final RegionService regionService;
    private final VoteHistoryService voteHistoryService;

    private final UserRepository userRepository;

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
    @GetMapping("/{regionId}/poll")
    @Operation(summary = "투표 여부 조회", description = "투표 여부를 조회합니다")
    public ResponseDto<?> hasUserVoted(@UserId Long userId, @PathVariable Long regionId) {
        return ResponseDto.ok(voteHistoryService.checkUserVoting(userId, regionId));
    }

    //3-2. 투표 하기
    @PostMapping("/{regionId}/poll")
    @Operation(summary = "투표 하기", description = "투표를 합니다.")
    public ResponseDto<?> createVote(@UserId Long userId, @PathVariable Long regionId, @RequestBody VoteRequestDto voteRequestDto) {
        voteHistoryService.createVote(regionId, voteRequestDto, userId);
        return ResponseDto.ok(null);
    }

}

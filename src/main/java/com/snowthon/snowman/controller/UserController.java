package com.snowthon.snowman.controller;


import com.snowthon.snowman.annotation.UserId;
import com.snowthon.snowman.contrant.Constants;
import com.snowthon.snowman.dto.common.ResponseDto;
import com.snowthon.snowman.repository.VoteHistoryRepository;
import com.snowthon.snowman.service.UserService;
import com.snowthon.snowman.service.VoteHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(Constants.API_PREFIX + "/users")
@Tag(name = "User", description = "유저 관련 API")
public class UserController {

    private final UserService userService;

    /**
     * TODO USERID
     *
     * @param userId
     * @return
     */
    //4-1. 모아 보기
    @GetMapping("/vote-history/{weatherId}")
    @Operation(summary = "아카이빙 모아보기", description = "그 지역 모든 유저들의 투표 기록들을 가져옵니다")
    public ResponseDto<?> archiving(@PathVariable("weatherId") Long regionId) {
        return ResponseDto.ok(userService.getVoteHistoriesByUser(regionId));
    }

    //4-2. 모아 보기(상세)
    @GetMapping("/vote-history/{archiveId}/details")
    @Operation(summary = "아카이빙 상세보기", description = "상세한 투표정보를 가져옵니다.")
    public ResponseDto<?> archivingDetail(@PathVariable Long archiveId) {
        return ResponseDto.ok(userService.getVoteHistoryById(archiveId));
    }

//    @PostMapping("/make-testUser")
//    @Operation(summary = "테스트 유저 만들기", description = "테스트 유저를 만듭니다.")
//    public ResponseDto<?> makeTestUser() {
//        userService.makeTestUser();
//        return ResponseDto.ok(null);
//    }

//    @PostMapping("/delete-allUser")
//    public ResponseDto<?> deleteAllDatabase() {
//        userService.deleteAllDatabase();
//        return ResponseDto.ok(null);
//    }
}

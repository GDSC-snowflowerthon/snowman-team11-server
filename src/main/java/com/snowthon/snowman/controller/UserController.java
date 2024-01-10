package com.snowthon.snowman.controller;


import com.snowthon.snowman.annotation.UserId;
import com.snowthon.snowman.contrant.Constants;
import com.snowthon.snowman.dto.common.ResponseDto;
import com.snowthon.snowman.repository.VoteHistoryRepository;
import com.snowthon.snowman.service.UserService;
import com.snowthon.snowman.service.VoteHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(Constants.API_PREFIX + "/users")
public class UserController {

    private final UserService userService;

    //4-1. 모아 보기
    @GetMapping("/vote-history")
    @Operation(summary = "아카이빙 모아보기", description = "유저의 투표 기록들을 가져옵니다")
    public ResponseDto<?> archiving(@UserId Long userId) {
        return ResponseDto.ok(userService.getVoteHistoriesByUser(userId));
    }

    //4-2. 모아 보기(상세)
    @GetMapping("/vote-history/{voteHistoryId}")
    @Operation(summary = "아카이빙 상세보기", description = "상세한 투표정보를 가져옵니다.")
    public ResponseDto<?> archivingDetail(@PathVariable Long voteHistoryId) {

        return ResponseDto.ok(userService.getVoteHistoryById(voteHistoryId));
    }

//    @PostMapping("/make-testUser")
//    @Operation(summary = "테스트 유저 만들기", description = "테스트 유저를 만듭니다.")
//    public ResponseDto<?> makeTestUser() {
//        userService.makeTestUser();
//        return ResponseDto.ok(null);
//    }

}

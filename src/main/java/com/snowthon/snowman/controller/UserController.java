package com.snowthon.snowman.controller;


import com.snowthon.snowman.annotation.UserId;
import com.snowthon.snowman.contrant.Constants;
import com.snowthon.snowman.dto.common.ResponseDto;
import com.snowthon.snowman.repository.VoteHistoryRepository;
import com.snowthon.snowman.service.VoteHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(Constants.API_PREFIX + "/users")
public class UserController {

    private final VoteHistoryService voteHistoryService;

    @GetMapping("/vote-history")
    @Operation(summary = "아카이빙 모아보기", description = "유저의 투표 기록들을 가져옵니다")
    public ResponseDto<?> archiving(@UserId Long userId) {
        return ResponseDto.ok(voteHistoryService.getVoteHistoriesByUser(userId));
    }

    @GetMapping("/vote-history/{voteHistoryId}")
    @Operation(summary = "아카이빙 상세보기", description = "상세한 투표정보를 가져옵니다.")
    public ResponseDto<?> archivingDetail(@PathVariable Long voteHistoryId) {

        return ResponseDto.ok(voteHistoryService.getVoteHistoryById(voteHistoryId));
    }

}

package com.snowthon.snowman.service;

import com.snowthon.snowman.domain.Branch;
import com.snowthon.snowman.domain.Region;
import com.snowthon.snowman.domain.User;
import com.snowthon.snowman.domain.VoteHistory;
import com.snowthon.snowman.dto.request.VoteRequestDto;
import com.snowthon.snowman.dto.type.ErrorCode;
import com.snowthon.snowman.exception.CommonException;
import com.snowthon.snowman.repository.RegionRepository;
import com.snowthon.snowman.repository.UserRepository;
import com.snowthon.snowman.repository.VoteHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteHistoryService {

    private final VoteHistoryRepository voteHistoryRepository;
    private final RegionRepository regionRepository;
    private final UserRepository userRepository;

    //3-2. 투표 하기
    @Transactional
    public void createVote(Long regionId, VoteRequestDto voteRequestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_REGION));

        Branch mainBranch = region.getMainBranch();
        mainBranch.updateVote(voteRequestDto.topWear(), voteRequestDto.outerWear(), voteRequestDto.headWear(), voteRequestDto.neckWear());

        VoteHistory voteHistory = VoteHistory.createFrom(user, region, mainBranch, voteRequestDto.topWear(), voteRequestDto.outerWear(), voteRequestDto.headWear(), voteRequestDto.neckWear());

        voteHistoryRepository.save(voteHistory);
    }
}

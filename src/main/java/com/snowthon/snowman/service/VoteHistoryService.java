package com.snowthon.snowman.service;

import com.snowthon.snowman.domain.Branch;
import com.snowthon.snowman.domain.Region;
import com.snowthon.snowman.domain.User;
import com.snowthon.snowman.domain.VoteHistory;
import com.snowthon.snowman.dto.request.VoteRequestDto;
import com.snowthon.snowman.repository.RegionRepository;
import com.snowthon.snowman.repository.VoteHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VoteHistoryService {

    private final VoteHistoryRepository voteHistoryRepository;
    private final RegionRepository regionRepository;

    @Transactional
    public void saveVote(Long regionId, VoteRequestDto requestDto, User user) {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new RuntimeException("Region not found")); // Replace with appropriate exception

        Branch mainBranch = region.getMainBranch();

        VoteHistory voteHistory = VoteHistory.createFrom(user, region, mainBranch, requestDto);

        mainBranch.getHeadWear().updateVote(requestDto.getHeadWear());
        mainBranch.getNeckWear().updateVote(requestDto.getNeckWear());
        mainBranch.getTopWear().updateVote(requestDto.getTopWear());
        mainBranch.getOuterWear().updateVote(requestDto.getOuter());

        voteHistoryRepository.save(voteHistory);
    }
}

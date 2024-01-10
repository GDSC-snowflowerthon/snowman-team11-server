package com.snowthon.snowman.service;

import com.snowthon.snowman.domain.*;
import com.snowthon.snowman.dto.request.VoteRequestDto;
import com.snowthon.snowman.repository.RegionRepository;
import com.snowthon.snowman.repository.UserRegionVoteRepository;
import com.snowthon.snowman.repository.UserRepository;
import com.snowthon.snowman.repository.VoteHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VoteHistoryService {

    private final VoteHistoryRepository voteHistoryRepository;
    private final UserRegionVoteRepository userRegionVoteRepository;
    private final RegionRepository regionRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveVote(Long regionId, VoteRequestDto requestDto, User user) {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new RuntimeException("Region not found")); // Replace with appropriate exception

        Branch mainBranch = region.getMainBranch();

        //VoteHistory 객체 성성 및 저장
        VoteHistory voteHistory = VoteHistory.createFrom(user, region, mainBranch, requestDto);

        mainBranch.getHeadWear().updateVote(requestDto.getHeadWear());
        mainBranch.getNeckWear().updateVote(requestDto.getNeckWear());
        mainBranch.getTopWear().updateVote(requestDto.getTopWear());
        mainBranch.getOuterWear().updateVote(requestDto.getOuter());

        voteHistoryRepository.save(voteHistory);

        //UserRegionVote 객체 생성 및 저장
        UserRegionVote userRegionVote = UserRegionVote.create(user, region);
        userRegionVoteRepository.save(userRegionVote);
    }

    //투표 여부 확인
    public boolean checkUserVoting(Long userId, Long regionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new RuntimeException("Region not found"));

        return userRegionVoteRepository.existsByUserAndRegion(user, region);
    }
}

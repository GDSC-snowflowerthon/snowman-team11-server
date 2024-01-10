package com.snowthon.snowman.service;

import com.snowthon.snowman.domain.*;
import com.snowthon.snowman.dto.request.VoteRequestDto;
import com.snowthon.snowman.dto.response.ArchivingDto;
import com.snowthon.snowman.dto.type.ErrorCode;
import com.snowthon.snowman.exception.CommonException;
import com.snowthon.snowman.repository.RegionRepository;
import com.snowthon.snowman.repository.UserRegionVoteRepository;
import com.snowthon.snowman.repository.UserRepository;
import com.snowthon.snowman.repository.VoteHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteHistoryService {

    private final VoteHistoryRepository voteHistoryRepository;
    private final UserRegionVoteRepository userRegionVoteRepository;
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

        //UserRegionVote 객체 생성 및 저장
        userRegionVoteRepository.save(UserRegionVote.create(user, region));
    }

    //3-1. 투표 여부 확인
    public boolean checkUserVoting(Long userId, Long regionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_REGION));

        return userRegionVoteRepository.existsByUserAndRegion(user, region);
    }




}

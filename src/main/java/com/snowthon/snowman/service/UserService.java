package com.snowthon.snowman.service;

import com.snowthon.snowman.domain.User;
import com.snowthon.snowman.domain.VoteHistory;
import com.snowthon.snowman.dto.response.ArchivingDto;
import com.snowthon.snowman.dto.type.ErrorCode;
import com.snowthon.snowman.exception.CommonException;
import com.snowthon.snowman.repository.ForecastDataRepository;
import com.snowthon.snowman.repository.UserRegionVoteRepository;
import com.snowthon.snowman.repository.UserRepository;
import com.snowthon.snowman.repository.VoteHistoryRepository;
import com.snowthon.snowman.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final VoteHistoryRepository voteHistoryRepository;
    private final UserRegionVoteRepository userRegionVoteRepository;
    private final ForecastDataRepository forecastDataRepository;

    private final JwtUtil jwtUtil;

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    //4-1. 모아 보기
    public ArchivingDto getVoteHistoriesByUser(Long regionId) {
        List<VoteHistory> voteHistoryList = voteHistoryRepository.findByRegionId(regionId);
        return ArchivingDto.fromEntity(voteHistoryList.stream()
                .map(ArchivingDto.ArchivingDetailDto::fromEntity).collect(Collectors.toList()));
    }


    //4-2. 모아 보기(상세)
    public ArchivingDto.ArchivingDetailDto getVoteHistoryById(Long voteHistoryId) {
        return ArchivingDto.ArchivingDetailDto.fromEntity(voteHistoryRepository.findById(voteHistoryId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_HISTORY)));
    }


//    @Transactional
//    public void deleteAllDatabase() {
//        voteHistoryRepository.deleteAll();
//        regionRepository.deleteAll();
//        forecastDataRepository.deleteAll();
//    }
}

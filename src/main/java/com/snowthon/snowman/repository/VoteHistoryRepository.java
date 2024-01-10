package com.snowthon.snowman.repository;

import com.snowthon.snowman.domain.VoteHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteHistoryRepository extends JpaRepository<VoteHistory, Long> {
    List<VoteHistory> findByUserId(Long userId);
}

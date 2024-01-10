package com.snowthon.snowman.repository;

import com.snowthon.snowman.domain.Region;
import com.snowthon.snowman.domain.User;
import com.snowthon.snowman.domain.UserRegionVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegionVoteRepository extends JpaRepository<UserRegionVote, Long> {
    boolean existsByUserAndRegion(User user, Region region);
}

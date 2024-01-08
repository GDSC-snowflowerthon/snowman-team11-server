package com.snowthon.snowman.repository.wear;

import com.snowthon.snowman.domain.wear.TopWear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopWearRepository extends JpaRepository<TopWear,Long> {
}

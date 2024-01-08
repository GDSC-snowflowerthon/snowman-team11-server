package com.snowthon.snowman.repository.wear;

import com.snowthon.snowman.domain.wear.OuterWear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OuterWearRepository extends JpaRepository<OuterWear, Long> {
}

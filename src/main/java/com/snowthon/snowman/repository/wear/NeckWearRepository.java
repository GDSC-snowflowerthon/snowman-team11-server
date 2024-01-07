package com.snowthon.snowman.repository.wear;

import com.snowthon.snowman.domain.wear.NeckWear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeckWearRepository extends JpaRepository<NeckWear, Long> {
}

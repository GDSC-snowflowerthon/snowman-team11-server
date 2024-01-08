package com.snowthon.snowman.repository.wear;

import com.snowthon.snowman.domain.wear.HeadWear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadWearRepository extends JpaRepository<HeadWear, Long>{
}

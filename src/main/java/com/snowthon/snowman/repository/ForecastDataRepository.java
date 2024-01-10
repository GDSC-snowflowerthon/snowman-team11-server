package com.snowthon.snowman.repository;

import com.snowthon.snowman.domain.Branch;
import com.snowthon.snowman.domain.ForecastData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForecastDataRepository extends JpaRepository<ForecastData, Long> {

    List<ForecastData> findByBranch(Branch branch);
}

package com.snowthon.snowman.repository;

import com.snowthon.snowman.domain.ForecastData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForecastDataRepository extends JpaRepository<ForecastData, Long> {
}

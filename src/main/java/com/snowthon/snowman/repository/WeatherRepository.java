package com.snowthon.snowman.repository;

import com.snowthon.snowman.domain.Weather;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    @EntityGraph(attributePaths = {"branch", "branch.topWear", "branch.outerWear", "branch.neckWear", "branch.headWear"})
    Optional<Weather> findByCode(String code);

}

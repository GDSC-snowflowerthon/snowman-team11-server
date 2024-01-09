package com.snowthon.snowman.domain;

import com.snowthon.snowman.dto.request.thirdParty.WeatherDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "forecast_data")
public class ForecastData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    /**
     * 예보종류는
     * TMP
     * SKY
     * 이렇게 두개에 따라서 결정
     */
    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "forecast_date", nullable = false)
    private String fcstDate; // ex) 20210103

    @Column(name = "forecast_time", nullable = false)
    private String fcstTime; // ex) 0600

    @Column(name = "forecast_value", nullable = false)
    private String fcstValue; // 예보 값

    @Builder
    public ForecastData(Branch branch, String category, String fcstDate, String fcstTime, String fcstValue) {
        this.branch = branch;
        this.category = category;
        this.fcstDate = fcstDate;
        this.fcstTime = fcstTime;
        this.fcstValue = fcstValue;
    }

    /*최고 기온을 반환 하는 함수*/
    public static Integer calculateHighestTemperature(List<ForecastData> forecastData) {
        return forecastData.stream()
                .filter(data -> "TMP".equals(data.getCategory()))
                .mapToInt(data -> Integer.parseInt(data.getFcstValue()))
                .max()
                .orElse(0);
    }

    /*최저 기온을 반환 하는 함수*/
    public static Integer calculateLowestTemperature(List<ForecastData> forecastData) {
        return forecastData.stream()
                .filter(data -> "TMP".equals(data.getCategory()))
                .mapToInt(data -> Integer.parseInt(data.getFcstValue()))
                .min()
                .orElse(0);
    }

    /*평균 기온을 반환하는 함수*/
    public static Float calculateAverageTemperature(List<ForecastData> forecastData) {
        List<ForecastData> temperatureData = forecastData.stream()
                .filter(data -> "TMP".equals(data.getCategory()))
                .toList();

        if (temperatureData.isEmpty()) {
            return null;
        }

        int totalTemperature = temperatureData.stream()
                .mapToInt(data -> Integer.parseInt(data.getFcstValue()))
                .sum();

        // 한 쪽을 float로 캐스팅하여 소수점 이하의 값을 얻습니다.
        log.info("data = {}",(float) totalTemperature / temperatureData.size());
        return (float) totalTemperature / temperatureData.size();
    }


    public static String getSkyCondition(List<ForecastData> forecastData) {
        return forecastData.stream()
                .filter(data -> "PTY".equals(data.getCategory()))
                .map(ForecastData::getFcstValue)
                .findFirst()
                .orElse("0");
    }
}

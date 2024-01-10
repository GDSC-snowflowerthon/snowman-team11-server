package com.snowthon.snowman.utility;

import com.snowthon.snowman.domain.Branch;
import com.snowthon.snowman.domain.ForecastData;
import com.snowthon.snowman.dto.type.EBranchType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ForecastDateUtil {
    public String findEarliestTime(List<ForecastData> forecastDataList) {
        return forecastDataList.stream()
                .map(data -> data.getFcstDate() + data.getFcstTime())
                .min(String::compareTo)
                .orElse("00000000");
    }

    public List<Branch> createBranchesFromForecastData(List<ForecastData> forecastDataList) {
        String startTime = findEarliestTime(forecastDataList);
        List<Branch> branches = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            String endTime = incrementTimeBySixHours(startTime);
            EBranchType branchType = EBranchType.values()[i];

            List<ForecastData> filteredData = filterForecastDataByTimeRange(forecastDataList, startTime, endTime);
            Branch branch = Branch.createBranchForType(branchType, filteredData);
            branches.add(branch);

            startTime = endTime;
        }

        return branches;
    }


    private List<ForecastData> filterForecastDataByTimeRange(List<ForecastData> forecastData, String startTime, String endTime) {
        return forecastData.stream()
                .filter(data -> isWithinTimeRange(data, startTime, endTime))
                .collect(Collectors.toList());
    }

    private boolean isWithinTimeRange(ForecastData data, String startTime, String endTime) {
        String dataTime = data.getFcstDate() + data.getFcstTime();
        return dataTime.compareTo(startTime) >= 0 && dataTime.compareTo(endTime) < 0;
    }

    private String incrementTimeBySixHours(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

        localDateTime = localDateTime.plusHours(6);

        return localDateTime.format(formatter);
    }
}
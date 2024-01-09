package com.snowthon.snowman.service;

import com.snowthon.snowman.contrant.Constants;
import com.snowthon.snowman.domain.Branch;
import com.snowthon.snowman.domain.Region;
import com.snowthon.snowman.dto.request.WeatherInfoDto;
import com.snowthon.snowman.dto.request.thirdParty.RegionDto;
import com.snowthon.snowman.dto.request.thirdParty.WeatherDto;
import com.snowthon.snowman.dto.type.EBranchTime;
import com.snowthon.snowman.repository.BranchRepository;
import com.snowthon.snowman.repository.RegionRepository;
import com.snowthon.snowman.utility.ForecastDateUtil;
import com.snowthon.snowman.utility.ReverseGeoUtil;
import com.snowthon.snowman.utility.WeatherUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionService {

    private final WeatherUtil weatherUtil;
    private final ReverseGeoUtil reverseGeoUtil;
    private final ForecastDateUtil forecastDateUtil;
    private final RegionRepository regionRepository;
    private final BranchRepository branchRepository;


    //2-1. 날씨 정보 조회
    @Transactional
    public WeatherInfoDto getWeather(double lat, double lng) {
        RegionDto regionDto = reverseGeoUtil.getRegion(lat, lng);
        Optional<RegionDto.Document> documentOptional = regionDto.getDocuments().stream()
                .filter(document -> Constants.REGION_TYPE.equals(document.getRegionType()))
                .findFirst();

        if (documentOptional.isPresent()) {
            RegionDto.Document document = documentOptional.get();
            String code = document.getCode();
            Optional<Region> weatherOptional = regionRepository.findByCode(code);

            if (weatherOptional.isPresent()) {
                return WeatherInfoDto.fromEntity(weatherOptional.get());
            } else {
                Map<String, String> baseDateAndTime = EBranchTime.getBaseTimeFromCurrentTime(LocalDateTime.now());
                List<Branch> branchList = forecastDateUtil.createBranchesFromForecastData(
                        WeatherDto.createFromWeatherDto(
                                weatherUtil.getWeather(
                                        lat,
                                        lng,
                                        baseDateAndTime.get("baseDate"),
                                        baseDateAndTime.get("baseTime")
                                )
                        )
                );

                log.info("branchList: {}", branchList);
                Region region = Region.createRegion(document.getAddressName(), code, branchList);
                branchList.forEach(branch -> branch.updateRegion(region));
                regionRepository.save(region);
                return WeatherInfoDto.fromEntity(region);

            }
        }
        return null; // 조건에 해당하는 Weather 정보가 없는 경우
    }


    /* 매 분기별 db삭제 */
    @Transactional
    @Scheduled(cron = "0 0 6,12,18,0 * * *")
    public void deleteAllRegions() {
        regionRepository.deleteAll();
    }


}

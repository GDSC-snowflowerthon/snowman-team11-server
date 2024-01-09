package com.snowthon.snowman.service;

import com.snowthon.snowman.contrant.Constants;
import com.snowthon.snowman.domain.Branch;
import com.snowthon.snowman.domain.ForecastData;
import com.snowthon.snowman.domain.Region;
import com.snowthon.snowman.dto.request.WeatherInfoDto;
import com.snowthon.snowman.dto.request.thirdParty.RegionDto;
import com.snowthon.snowman.dto.request.thirdParty.WeatherDto;
import com.snowthon.snowman.dto.type.EBranchTime;
import com.snowthon.snowman.dto.type.ErrorCode;
import com.snowthon.snowman.exception.CommonException;
import com.snowthon.snowman.repository.BranchRepository;
import com.snowthon.snowman.repository.ForecastDataRepository;
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
    private final ForecastDataRepository forecastDataRepository;
    private final ForecastDateUtil forecastDateUtil;
    private final RegionRepository regionRepository;
    private final BranchRepository branchRepository;


    //2-1. 날씨 정보 조회
    @Transactional
    public WeatherInfoDto getWeather(double lat, double lng) {
        RegionDto.Document document = reverseGeoUtil.getRegion(lat, lng).getDocuments().stream()
                .filter(doc -> Constants.REGION_TYPE.equals(doc.getRegionType()))
                .findFirst()
                .orElseThrow(() -> new CommonException(ErrorCode.THIRD_PARTY_API_ERROR));

        Region region = regionRepository.findByCode(document.getCode())
                .orElseGet(() -> {
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

                    Region newRegion = Region.createRegion(document.getAddressName(), document.getCode(), branchList);
                    branchList.forEach(branch -> branch.updateRegion(newRegion));
                    return regionRepository.save(newRegion);
                });

        return WeatherInfoDto.fromEntity(region);
    }


    /* 매 분기별 db삭제 */
    @Transactional
    @Scheduled(cron = "0 0 6,12,18,0 * * *")
    public void deleteAllRegions() {
        regionRepository.deleteAll();
    }


}

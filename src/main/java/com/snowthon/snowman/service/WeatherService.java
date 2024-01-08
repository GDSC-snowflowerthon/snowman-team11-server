package com.snowthon.snowman.service;

import com.snowthon.snowman.contrant.Constants;
import com.snowthon.snowman.domain.Weather;
import com.snowthon.snowman.dto.request.WeatherInfoDto;
import com.snowthon.snowman.dto.request.thirdParty.RegionDto;
import com.snowthon.snowman.dto.request.thirdParty.WeatherDto;
import com.snowthon.snowman.dto.type.EBranchTime;
import com.snowthon.snowman.repository.WeatherRepository;
import com.snowthon.snowman.utility.DateUtil;
import com.snowthon.snowman.utility.ReverseGeoUtil;
import com.snowthon.snowman.utility.WeatherUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherUtil weatherUtil;
    private final ReverseGeoUtil reverseGeoUtil;
    private final DateUtil dateUtil;
    private final WeatherRepository weatherRepository;


    public WeatherInfoDto getWeather(double lat, double lng) {
        RegionDto region = reverseGeoUtil.getRegion(lat, lng);
        Optional<RegionDto.Document> documentOptional = region.getDocuments().stream()
                .filter(document -> Constants.REGION_TYPE.equals(document.getRegionType()))
                .findFirst();

        if (documentOptional.isPresent()) {
            RegionDto.Document document = documentOptional.get();
            String code = document.getCode();
            String addressName = document.getAddressName();

            Optional<Weather> weatherOptional = weatherRepository.findByCode(code);

            if (weatherOptional.isPresent()) {
                return WeatherInfoDto.fromEntity(weatherOptional.get());
            } else {
                Map<String, String> baseDateAndTime = EBranchTime.getBaseTimeFromCurrentTime(LocalDateTime.now());
                WeatherDto weather = weatherUtil.getWeather(lat, lng, baseDateAndTime.get("baseDate"), baseDateAndTime.get("baseTime"));

            }
        }
        return null; // 조건에 해당하는 Weather 정보가 없는 경우
    }



}

package com.snowthon.snowman.service;

import com.snowthon.snowman.contrant.Constants;
import com.snowthon.snowman.domain.Weather;
import com.snowthon.snowman.dto.request.WeatherInfoDto;
import com.snowthon.snowman.dto.request.thirdParty.RegionDto;
import com.snowthon.snowman.repository.WeatherRepository;
import com.snowthon.snowman.utility.ReverseGeoUtil;
import com.snowthon.snowman.utility.WeatherUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherUtil weatherUtil;
    private final ReverseGeoUtil reverseGeoUtil;
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
            weatherRepository.findByCode(code)
                    .ifPresentOrElse(
                            weather -> {
                                 return WeatherInfoDto.
                            },
                            () -> weatherRepository.save(Weather.builder()
                                    .code(code)
                                    .addressName(addressName)
                                    .build())

        }

    }



}

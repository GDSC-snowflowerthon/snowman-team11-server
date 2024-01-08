package com.snowthon.snowman.service;

import com.snowthon.snowman.dto.request.thirdParty.RegionDto;
import com.snowthon.snowman.utility.ReverseGeoUtil;
import com.snowthon.snowman.utility.WeatherUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherUtil weatherUtil;
    private final ReverseGeoUtil reverseGeoUtil;

    public RegionDto getWeather(double lat, double lng){

        return reverseGeoUtil.getRegion(lat, lng);
    }

}

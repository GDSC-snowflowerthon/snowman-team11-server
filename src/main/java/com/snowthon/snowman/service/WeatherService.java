package com.snowthon.snowman.service;

import com.snowthon.snowman.dto.request.RegionDto;
import com.snowthon.snowman.dto.request.WeatherDto;
import com.snowthon.snowman.utility.ReverseGeoUtil;
import com.snowthon.snowman.utility.WeatherUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.generic.ClassGen;
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

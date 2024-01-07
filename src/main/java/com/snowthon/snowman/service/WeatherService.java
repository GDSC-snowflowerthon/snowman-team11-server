package com.snowthon.snowman.service;

import com.snowthon.snowman.dto.request.WeatherDto;
import com.snowthon.snowman.utility.WeatherUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherUtil weatherUtil;

    public WeatherDto getWeather(double lat, double lng){
        return weatherUtil.getWeather(lat, lng);
    }

}

package com.snowthon.snowman.utility;

import com.snowthon.snowman.dto.request.RegionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReverseGeoUtil {

    @Value("${reverse-geocoding.api.url}")
    private String url;

    @Value("${reverse-geocoding.api.header}")
    private String header;

    private final WebClientUtil webClientUtil;

    public RegionDto getRegion(double lat, double lng) {
        Map<String, String> headers = Collections.singletonMap("Authorization", header);

        return webClientUtil.get(
                url + "?x=" + lng + "&y=" + lat,
                RegionDto.class,
                headers
        );
    }

}

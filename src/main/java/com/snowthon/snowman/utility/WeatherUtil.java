package com.snowthon.snowman.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WeatherUtil {

    static final double RE = 6371.00877; // 지구 반경(km)
    static final double GRID = 5.0; // 격자 간격(km)
    static final double SLAT1 = 30.0; // 투영 위도1(degree)
    static final double SLAT2 = 60.0; // 투영 위도2(degree)
    static final double OLON = 126.0; // 기준점 경도(degree)
    static final double OLAT = 38.0; // 기준점 위도(degree)
    static final double XO = 43.0; // 기준점 X좌표(GRID)
    static final double YO = 136.0; // 기준점 Y좌표(GRID)

    @Value("${weather.api.url}")
    private String url;

    @Value("${weather.api.key}")
    private String secretKey;

    @Value("${weather.api.page-no}")
    private String pageNo;

    @Value("${weather.api.num-of-rows}")
    private String numOfRows;

    @Value("${weather.api.data-type}")
    private String dataType;


    //base_date, base_time, nx, ny는 각각 받아서 넣어줘야함

    public static Map<String, Integer> transToXY(double lat, double lng) {
        final double DEGRAD = Math.PI / 180.0;
        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double tan_slat1 = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        double sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) /
                Math.log(Math.tan(Math.PI * 0.25 + slat2 * 0.5) / tan_slat1);
        double sf = Math.pow(tan_slat1, sn) * Math.cos(slat1) / sn;
        double ro = re * sf / Math.pow(Math.tan(Math.PI * 0.25 + olat * 0.5), sn);

        double ra = Math.tan(Math.PI * 0.25 + lat * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);

        double theta = lng * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;

        int nx = (int) Math.floor(ra * Math.sin(theta) + XO + 0.5);
        int ny = (int) Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);

        Map<String, Integer> rs = new HashMap<>();
        rs.put("nx", nx);
        rs.put("ny", ny);
        return rs;
    }


}

package com.snowthon.snowman.dto.type;

import lombok.Getter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum EBranchTime {

    MORNING("오전", "0500"),
    AFTERNOON("오후", "1100"),
    NIGHT("밤", "1700"),
    DAWN("새벽", "2300");

    private final String value;
    private final String baseTime;

    EBranchTime(String value, String baseTime) {
        this.value = value;
        this.baseTime = baseTime;
    }

    public static Map<String, String> getBaseTimeFromCurrentTime(LocalDateTime currentTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        int hour = currentTime.getHour();

        Map<String, String> res = new HashMap<>();
        String baseDate;
        String baseTime;

        if (hour < 6) {
            baseDate = currentTime.minusDays(1).format(dateFormatter);
            baseTime = DAWN.getBaseTime();
        } else {
            baseDate = currentTime.format(dateFormatter);
            if (hour < 12) {
                baseTime = MORNING.getBaseTime();
            } else if (hour < 18) {
                baseTime = AFTERNOON.getBaseTime();
            } else {
                baseTime = NIGHT.getBaseTime();
            }
        }

        res.put("baseDate", baseDate);
        res.put("baseTime", baseTime);

        return res;
    }
}

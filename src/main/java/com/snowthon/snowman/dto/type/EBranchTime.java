package com.snowthon.snowman.dto.type;

import lombok.Getter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public enum EBranchTime {

    MORNING("오전"),
    AFTERNOON("오후"),
    NIGHT("밤"),
    DAWN("새벽");

    private final String value;

    EBranchTime(String value) {
        this.value = value;
    }

    public static String fromTime(String inputTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일");
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime inputDateTime = LocalDateTime.of(currentTime.toLocalDate(), LocalDateTime.parse(inputTime, timeFormatter).toLocalTime());

        if (inputDateTime.isBefore(currentTime)) {
            inputDateTime = inputDateTime.plusDays(1);
        }

        int hour = inputDateTime.getHour();
        String dayString = inputDateTime.format(dateFormatter);

        if (hour >= 6 && hour < 12) {
            return dayString + " " + MORNING.getValue();
        } else if (hour >= 12 && hour < 18) {
            return dayString + " " + AFTERNOON.getValue();
        } else if (hour >= 18) {
            return dayString + " " + NIGHT.getValue();
        } else {
            return dayString + " " + DAWN.getValue();
        }
    }
}

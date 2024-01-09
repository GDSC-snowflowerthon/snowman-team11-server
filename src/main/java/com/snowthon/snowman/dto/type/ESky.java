package com.snowthon.snowman.dto.type;

import lombok.Getter;

@Getter
public enum ESky {
    CLEAR("맑음"),
    RAIN("비"),
    SNOW("눈"),

    ;
     //없음(0), 비(1,4), 비/눈(2), 눈(3)

    private final String description;

    ESky(String description) {
        this.description = description;
    }

    public static ESky getSky(String skyCode) {
        return switch (skyCode) {
            case "0" -> CLEAR;
            case "1", "4" -> RAIN;
            case "2", "3" -> SNOW;
            default -> null;
        };
    }

}

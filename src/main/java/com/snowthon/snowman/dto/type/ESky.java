package com.snowthon.snowman.dto.type;

import lombok.Getter;

@Getter
public enum ESky {
    CLEAR("맑음"),
    RAIN("비"),
    SNOW("눈"),

    ;

    private final String description;

    ESky(String description) {
        this.description = description;
    }

    public static ESky getSky(String sky) {
        if (sky.contains("맑음")) {
            return CLEAR;
        } else if (sky.contains("비")) {
            return RAIN;
        } else {
            return SNOW;
        }
    }
}

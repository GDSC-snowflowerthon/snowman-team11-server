package com.snowthon.snowman.dto.type;

import lombok.Getter;

@Getter
public enum ELevel {
    LEVEL_1("영하 15도 이하"),
    LEVEL_2("영하 15도 초과, 영하 10도 이하"),
    LEVEL_3("영하 10도 초과, 영하 5도 이하"),
    LEVEL_4("영하 5도 초과, 영하 0도 이하"),
    LEVEL_5("영하 0도 초과, 영상 5도 이하"),
    LEVEL_6("영상 5도 초과, 영상 10도 이하"),
    LEVEL_7("영상 10도 초과, 영상 15도 이하"),
    LEVEL_8("영상 15도 초과"),


    ;

    private final String description;


    ELevel(String description) {
        this.description = description;
    }

    public static ELevel getLevel(int temperature) {
        if (temperature <= -15) {
            return LEVEL_1;
        } else if (temperature <= -10) {
            return LEVEL_2;
        } else if (temperature <= -5) {
            return LEVEL_3;
        } else if (temperature <= 0) {
            return LEVEL_4;
        } else if (temperature <= 5) {
            return LEVEL_5;
        } else if (temperature <= 10) {
            return LEVEL_6;
        } else if (temperature <= 15) {
            return LEVEL_7;
        } else {
            return LEVEL_8;
        }
    }
}

package com.snowthon.snowman.dto.type.wear;

import lombok.Getter;

@Getter
public enum ETopWear {
    NEAT("니트"),
    LONG_SLEEVE("긴 팔"),

    ;

    private final String value;

    ETopWear(String value) {
        this.value = value;
    }

    public static ETopWear fromValue(String value) {
        for (ETopWear topWear : ETopWear.values()) {
            if (topWear.getValue().equals(value)) {
                return topWear;
            }
        }
        return null;
    }
}

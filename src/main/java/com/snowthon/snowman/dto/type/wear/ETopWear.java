package com.snowthon.snowman.dto.type.wear;

import lombok.Getter;

@Getter
public enum ETopWear implements Wear{
    NEAT("니트"),
    LONG_SLEEVE("긴 팔"),

    ;
    /**
     * 상의는 필수
     */

    private final String value;

    ETopWear(String value) {
        this.value = value;
    }

    @Override
    public Wear fromValue(String value) {
        for (ETopWear topWear : ETopWear.values()) {
            if (topWear.getValue().equals(value)) {
                return topWear;
            }
        }
        return null;
    }
}

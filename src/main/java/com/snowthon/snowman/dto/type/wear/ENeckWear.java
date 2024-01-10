package com.snowthon.snowman.dto.type.wear;

import lombok.Getter;

@Getter
public enum ENeckWear implements Wear{
    SCARF("스카프"),
    NONE("없음"),

    ;
    /**
     * 목도리는 선택
     */

    private final String value;

    ENeckWear(String value) {
        this.value = value;
    }

    public static ENeckWear fromValue(String value) {
        for (ENeckWear neckWear : ENeckWear.values()) {
            if (neckWear.getValue().equals(value)) {
                return neckWear;
            }
        }
        return null;
    }

}

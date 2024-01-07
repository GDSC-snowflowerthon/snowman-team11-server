package com.snowthon.snowman.dto.type.wear;

import lombok.Getter;

@Getter
public enum ENeckWear {
    SCARF("스카프"),


    ;

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

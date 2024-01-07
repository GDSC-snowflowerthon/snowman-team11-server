package com.snowthon.snowman.dto.type.wear;

import lombok.Getter;

@Getter
public enum EOuter {
    LONG_PADDING("롱패딩"),
    SHORT_PADDING("숏패딩"),
    COAT("코트"),

    ;


    private final String value;

    EOuter(String value) {
        this.value = value;
    }

    public static EOuter fromValue(String value) {
        for (EOuter outer : EOuter.values()) {
            if (outer.getValue().equals(value)) {
                return outer;
            }
        }
        return null;
    }
}
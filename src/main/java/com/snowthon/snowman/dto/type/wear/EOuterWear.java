package com.snowthon.snowman.dto.type.wear;

import lombok.Getter;

@Getter
public enum EOuterWear implements Wear{
    LONG_PADDING("롱패딩"),
    SHORT_PADDING("숏패딩"),
    COAT("코트"),
    NONE("없음"),
    ;
    /**
     * 겉옷은 선택
     */

    private final String value;

    EOuterWear(String value) {
        this.value = value;
    }

    public static EOuterWear fromValue(String value) {
        for (EOuterWear outer : EOuterWear.values()) {
            if (outer.getValue().equals(value)) {
                return outer;
            }
        }
        return null;
    }
}
package com.snowthon.snowman.dto.type.wear;

import lombok.Getter;

@Getter
public enum EHeadWear {
    EAR_MUFFS("귀마개"),
    BALACLAVA("바라클라바"),

    ;

    private final String value;

    EHeadWear(String value) {
        this.value = value;
    }

    public static EHeadWear fromValue(String value) {
        for (EHeadWear headWear : EHeadWear.values()) {
            if (headWear.name().equals(value)) {
                return headWear;
            }
        }
        return null;
    }
}

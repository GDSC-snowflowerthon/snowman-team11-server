package com.snowthon.snowman.dto.type.wear;

import lombok.Getter;

@Getter
public enum EHeadWear implements Wear{
    EAR_MUFFS("귀마개"),
    BALACLAVA("바라클라바"),
    NONE("없음"),


    ;

    /**
     * 모자는 선택
     */

    private final String value;

    EHeadWear(String value) {
        this.value = value;
    }

    @Override
    public EHeadWear fromValue(String value) {
        for (EHeadWear headWear : EHeadWear.values()) {
            if (headWear.name().equals(value)) {
                return headWear;
            }
        }
        return null;
    }
}

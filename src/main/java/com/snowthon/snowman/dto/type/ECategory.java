package com.snowthon.snowman.dto.type;

import lombok.Getter;

@Getter
public enum ECategory {

    SKY("하늘상태"),
    TMP("1시간 기온"),


    ;
    private final String value;

    ECategory(String value) {
        this.value = value;
    }

    public static ECategory fromValue(String value) {
        for (ECategory category : ECategory.values()) {
            if (category.getValue().equals(value)) {
                return category;
            }
        }
        return null;
    }
}

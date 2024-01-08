package com.snowthon.snowman.dto.type;

import lombok.Getter;

@Getter
public enum EBranchType {

    MAIN_BRANCH("mainBranch"),
    FIRST_BRANCH("firstBranch"),
    SECOND_BRANCH("secondBranch"),
    THIRD_BRANCH("thirdBranch"),
    ;

    private final String value;

    EBranchType(String value) {
        this.value = value;
    }

    public static EBranchType fromValue(String value) {
        for (EBranchType branchType : EBranchType.values()) {
            if (branchType.getValue().equals(value)) {
                return branchType;
            }
        }
        return null;
    }

}

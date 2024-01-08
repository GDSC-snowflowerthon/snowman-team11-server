package com.snowthon.snowman.dto.request.thirdParty;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegionDto {
    private Meta meta;
    private List<Document> documents;

    @Builder
    public RegionDto(Meta meta, List<Document> documents) {
        this.meta = meta;
        this.documents = documents;
    }

    @Getter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Meta {
        private int totalCount;
    }

    @Getter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Document {
        private String regionType;
        private String addressName;
        private String region1depthName;
        private String region2depthName;
        private String region3depthName;
        private String region4depthName;
        private String code;
        private double x;
        private double y;
    }
}
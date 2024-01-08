package com.snowthon.snowman.dto.request.thirdParty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
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
    public static class Meta {
        private int total_count;
    }

    @Getter
    @NoArgsConstructor
    public static class Document {
        private String region_type;
        private String address_name;
        private String region_1depth_name;
        private String region_2depth_name;
        private String region_3depth_name;
        private String region_4depth_name;
        private String code;
        private double x;
        private double y;
    }
}
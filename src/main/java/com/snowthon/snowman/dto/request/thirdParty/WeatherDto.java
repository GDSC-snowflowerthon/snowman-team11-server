package com.snowthon.snowman.dto.request.thirdParty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class WeatherDto {
    private Response response;

    @Builder
    public WeatherDto(Response response) {
        this.response = response;
    }

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Body body;

        @Builder
        public Response(Body body) {
            this.body = body;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Body {
        private Items items;

        @Builder
        public Body(Items items) {
            this.items = items;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Items {
        private List<Item> item;

        @Builder
        public Items(List<Item> item) {
            this.item = item;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Item {
        private String category;
        private String fcstDate;
        private String fcstTime;
        private String fcstValue;

        @Builder
        public Item(String category, String fcstDate, String fcstTime, String fcstValue) {
            this.category = category;
            this.fcstDate = fcstDate;
            this.fcstTime = fcstTime;
            this.fcstValue = fcstValue;
        }
    }
}

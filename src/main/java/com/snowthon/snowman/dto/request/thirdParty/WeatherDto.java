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
    /**
     * 카테고리에서 필요한 값
     * SKY : 없음(0), 비(1,4), 비/눈(2), 눈(3)
     * TMP : 1시간 기온
     * 최고 최저는 불러오는 값으로 평균을 내서 해결을 해야할 듯
     *
     * baseTime 2300으로 불러오면 그 다음날 0000부터 1시간 단위로 불러와서 다음날 0000까지 불러옴
     *
     * ex) baseDate = 20240107, baseTime = 2300 이면
     * 2024년 1월 8일 0000부터 2023년 1월 9일 0000까지 불러옴
     *
     * baseTime은 고정값
     * 0500 1100 1700 2300
     *
     * baseDate는 현재 시간에 따라서 달라짐
     *
     *
     * 결과적인 로직
     * 1. 데이터를 불러와서 필요한 것은 그 시간대의 하늘의 상태
     * 2. 각 분기별 평균 온도
     * 3. 각 시간대별 온도를 저장할 필요가 있다.(메인브렌치에서 온도를 1시간마다 변경해야함)
     */
}

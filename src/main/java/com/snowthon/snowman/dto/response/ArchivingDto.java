package com.snowthon.snowman.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.snowthon.snowman.domain.VoteHistory;
import com.snowthon.snowman.dto.type.ESky;
import com.snowthon.snowman.dto.type.wear.EHeadWear;
import com.snowthon.snowman.dto.type.wear.ENeckWear;
import com.snowthon.snowman.dto.type.wear.EOuterWear;
import com.snowthon.snowman.dto.type.wear.ETopWear;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ArchivingDto{

        @JsonProperty("voteHistoryList") @Schema(description = "투표 내역 리스트")
        private List<ArchivingDetailDto> voteHistoryList;

        @Getter
        @NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class ArchivingDetailDto {
                @JsonProperty("archive_id") @Schema(description = "아카이브 아이디", example = "1")
                @NotNull(message = "이름은 필수입니다.")
                private Long archiveId;

                @JsonProperty("topWear") @Schema(description = "상의 정보", example = "LONG_SLEEVE")
                @NotNull(message = "topWear는 필수입니다.")
                private ETopWear topWear;

                @JsonProperty("outerWear") @Schema(description = "아우터 정보", example = "COAT")
                @NotNull(message = "outerWear는 필수입니다.")
                private EOuterWear outerWear;

                @JsonProperty("headWear") @Schema(description = "모자 정보", example = "EAR_MUFFS")
                @NotNull(message = "headWear는 필수입니다.")
                private EHeadWear headWear;

                @JsonProperty("neckWear") @Schema(description = "목도리 정보", example = "SCARF")
                @NotNull(message = "neckWear는 필수입니다.")
                private ENeckWear neckWear;

                @JsonProperty("weatherStatus") @Schema(description = "날씨 정보", example = "SNOW")
                @NotNull(message = "날씨 정보는 필수입니다.")
                private ESky weatherStatus;

                @JsonProperty("temperature") @Schema(description = "온도", example = "10")
                @NotNull(message = "temperature는 필수입니다.")
                private int temperature;

                @JsonProperty("location") @Schema(description = "위치", example = "서울특별시 중구 명동")
                @NotNull(message = "location는 필수입니다.")
                private String location;

                @JsonProperty("voteTime") @Schema(description = "투표 시간", example = "2024.01.12")
                @NotNull(message = "voteTime는 필수입니다.")
                private String voteTime;

                @Builder
                public ArchivingDetailDto(Long archiveId, ETopWear topWear, EOuterWear outerWear, EHeadWear headWear, ENeckWear neckWear, ESky weatherStatus, int temperature, String location, String voteTime) {
                        this.archiveId = archiveId;
                        this.topWear = topWear;
                        this.outerWear = outerWear;
                        this.headWear = headWear;
                        this.neckWear = neckWear;
                        this.weatherStatus = weatherStatus;
                        this.temperature = temperature;
                        this.location = location;
                        this.voteTime = voteTime;
                }

                public static ArchivingDetailDto fromEntity(VoteHistory voteHistory) {
                        return ArchivingDetailDto.builder()
                                .archiveId(voteHistory.getId())
                                .topWear(voteHistory.getTopWear())
                                .outerWear(voteHistory.getOuterWear())
                                .headWear(voteHistory.getHeadWear())
                                .neckWear(voteHistory.getNeckWear())
                                .weatherStatus(voteHistory.getSky())
                                .temperature(voteHistory.getTemperature())
                                .location(voteHistory.getLocation())
                                .voteTime(voteHistory.getVoteTime().format(java.time.format.DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                                .build();
                }
        }

        @Builder
        public ArchivingDto(List<ArchivingDetailDto> voteHistoryList) {
                this.voteHistoryList = voteHistoryList;
        }

        public static ArchivingDto fromEntity(List<ArchivingDetailDto> voteHistoryList) {
                return ArchivingDto.builder()
                        .voteHistoryList(voteHistoryList)
                        .build();
        }
}




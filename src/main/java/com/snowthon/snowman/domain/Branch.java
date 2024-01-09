package com.snowthon.snowman.domain;

import com.snowthon.snowman.domain.wear.HeadWear;
import com.snowthon.snowman.domain.wear.NeckWear;
import com.snowthon.snowman.domain.wear.OuterWear;
import com.snowthon.snowman.domain.wear.TopWear;
import com.snowthon.snowman.dto.type.EBranchType;
import com.snowthon.snowman.dto.type.ELevel;
import com.snowthon.snowman.dto.type.ESky;
import com.snowthon.snowman.dto.type.wear.EHeadWear;
import com.snowthon.snowman.dto.type.wear.ENeckWear;
import com.snowthon.snowman.dto.type.wear.EOuterWear;
import com.snowthon.snowman.dto.type.wear.ETopWear;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "branch")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "outer_wear_id")
    private OuterWear outerWear;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "top_wear_id")
    private TopWear topWear;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "neck_wear_id")
    private NeckWear neckWear;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "head_wear_id")
    private HeadWear headWear;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ForecastData> forecastData;


    @Column(name ="sky")
    private ESky sky; //눈 비 구분

    @Column(name = "temperature")
    private Integer temperature; //온도 메인브랜치에서 필요한 값

    /**
     * highestTemperature, lowestTemperature
     * <p>
     * mainBranch일 경우 최고, 최저 null
     * 다른 브랜치의 경우 필수
     */
    @Column(name = "lowest_temperature")
    private Integer highestTemperature;

    @Column(name = "highest_temperature")
    private Integer lowestTemperature;

    @Column(name = "branch_time")
    private String branchTime; // ex) 1월 3일 오후

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(name = "branch_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EBranchType branchType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Branch(OuterWear outerWear, TopWear topWear, NeckWear neckWear, HeadWear headWear, List<ForecastData> forecastData, ESky sky, Integer temperature, Integer highestTemperature, Integer lowestTemperature, String branchTime, Region region, EBranchType branchType) {
        this.outerWear = outerWear;
        this.topWear = topWear;
        this.neckWear = neckWear;
        this.headWear = headWear;
        this.forecastData = forecastData;
        this.sky = sky;
        this.temperature = temperature;
        this.highestTemperature = highestTemperature;
        this.lowestTemperature = lowestTemperature;
        this.branchTime = branchTime;
        this.region = region;
        this.branchType = branchType;
        this.createdAt = LocalDateTime.now();
    }


    // createBranchForType 메서드 수정
    public static Branch createBranchForType(EBranchType branchType, List<ForecastData> filteredData) {
        Integer highestTemperature = ForecastData.calculateHighestTemperature(filteredData);
        Integer lowestTemperature = ForecastData.calculateLowestTemperature(filteredData);
        Float averageTemperature = ForecastData.calculateAverageTemperature(filteredData);
        ESky sky = ESky.getSky(ForecastData.getSkyCondition(filteredData));
        ELevel level = ELevel.getLevel(averageTemperature);
        TopWear topWear = TopWear.createTopWear(1, 1, level);
        OuterWear outerWear = OuterWear.createOuterWear(1, 1, 1, level);
        NeckWear neckWear = NeckWear.createNeckWear(1, level);
        HeadWear headWear = HeadWear.createHeadWear(1, 1, level);
        String branchTime = formatBranchTime(filteredData);


        return switch (branchType) {
            case MAIN_BRANCH ->
                    Branch.createMainBranch(topWear,outerWear,neckWear, headWear, sky, filteredData);
            case FIRST_BRANCH ->
                    Branch.createFirstBranch(highestTemperature, lowestTemperature, topWear,outerWear,neckWear, headWear, branchTime, filteredData);
            case SECOND_BRANCH ->
                    Branch.createSecondBranch(highestTemperature, lowestTemperature, topWear,outerWear,neckWear, headWear, branchTime, filteredData);
            case THIRD_BRANCH ->
                    Branch.createThirdBranch(highestTemperature, lowestTemperature, topWear,outerWear,neckWear, headWear, branchTime, filteredData);
            default -> throw new IllegalArgumentException("Invalid branch type");
        };
    }

    private static String formatBranchTime(List<ForecastData> filteredData){
        String date = filteredData.get(0).getFcstDate();
        String time = filteredData.get(0).getFcstTime();

        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("MM월 dd일");
        LocalDate localDate = LocalDate.parse(date, originalFormatter);
        String formattedDate = localDate.format(targetFormatter);

        String partOfDay = getPartOfDay(time);
        return formattedDate + " " + partOfDay;
    }

    private static String getPartOfDay(String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        if (hour >= 0 && hour < 6) return "새벽";
        else if (hour >= 6 && hour < 12) return "오전";
        else if (hour >= 12 && hour < 18) return "오후";
        else return "밤";
    }

    public static Branch createMainBranch(TopWear topWear, OuterWear outerWear, NeckWear neckWear, HeadWear headWear, ESky sky, List<ForecastData> forecastDataList) {
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmm"));
        ForecastData currentData = findCurrentForecastData(forecastDataList, currentTime);


        if (currentData != null) {
            Integer temperature = currentData.getCategory().equals("TMP") ? Integer.parseInt(currentData.getFcstValue()) : null;

            return Branch.builder()
                    .branchType(EBranchType.MAIN_BRANCH)
                    .sky(sky)
                    .temperature(temperature)
                    .forecastData(forecastDataList)
                    .topWear(topWear)
                    .outerWear(outerWear)
                    .neckWear(neckWear)
                    .headWear(headWear)
                    .build();
        } else {
            log.info("error!!!");
            return null; // 적절한 데이터가 없는 경우 null 반환
        }
    }


    public void updateMainBranch(List<ForecastData> forecastDataList) {
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmm"));
        ForecastData currentData = findCurrentForecastData(forecastDataList, currentTime);

        if (currentData != null) {
            this.sky = ESky.getSky(currentData.getCategory().equals("SKY") ? currentData.getFcstValue() : String.valueOf(ESky.CLEAR));
            this.temperature = currentData.getCategory().equals("TMP") ? Integer.parseInt(currentData.getFcstValue()) : this.temperature;
        }
    }

    private static ForecastData findCurrentForecastData(List<ForecastData> forecastDataList, String currentTime) {
        String roundedCurrentTime = currentTime.substring(0, 2) + "00";
        return forecastDataList.stream()
                .filter(data -> data.getFcstTime().equals(roundedCurrentTime))
                .max(Comparator.comparing(ForecastData::getFcstDate)) // 최신 날짜 데이터 선택
                .orElse(null);
    }


    public static Branch createFirstBranch(Integer highestTemperature, Integer lowestTemperature, TopWear topWear, OuterWear outerWear, NeckWear neckWear, HeadWear headWear, String branchTime, List<ForecastData> filteredData) {
        return Branch.builder()
                .branchType(EBranchType.FIRST_BRANCH)
                .branchTime(branchTime)
                .highestTemperature(highestTemperature)
                .lowestTemperature(lowestTemperature)
                .outerWear(outerWear)
                .topWear(topWear)
                .neckWear(neckWear)
                .headWear(headWear)
                .forecastData(filteredData)
                .build();
    }

    public static Branch createSecondBranch(Integer highestTemperature, Integer lowestTemperature, TopWear topWear, OuterWear outerWear, NeckWear neckWear, HeadWear headWear, String branchTime, List<ForecastData> filteredData) {
        return Branch.builder()
                .branchType(EBranchType.SECOND_BRANCH)
                .branchTime(branchTime)
                .highestTemperature(highestTemperature)
                .lowestTemperature(lowestTemperature)
                .outerWear(outerWear)
                .topWear(topWear)
                .neckWear(neckWear)
                .headWear(headWear)
                .forecastData(filteredData)
                .build();
    }

    public static Branch createThirdBranch(Integer highestTemperature, Integer lowestTemperature, TopWear topWear, OuterWear outerWear, NeckWear neckWear, HeadWear headWear, String branchTime, List<ForecastData> filteredData) {
        return Branch.builder()
                .branchType(EBranchType.THIRD_BRANCH)
                .branchTime(branchTime)
                .highestTemperature(highestTemperature)
                .lowestTemperature(lowestTemperature)
                .outerWear(outerWear)
                .topWear(topWear)
                .neckWear(neckWear)
                .headWear(headWear)
                .forecastData(filteredData)
                .build();
    }


    public void updateRegion(Region region){
        this.region = region;
    }

    public void updateVote(ETopWear topWear, EOuterWear outerWear, EHeadWear headWear, ENeckWear neckWear) {
        this.topWear.updateVote(topWear);
        this.outerWear.updateVote(outerWear);
        this.headWear.updateVote(headWear);
        this.neckWear.updateVote(neckWear);
    }
}

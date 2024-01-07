package com.snowthon.snowman.domain.cnt;

import com.snowthon.snowman.domain.Weather;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "top_wear_cnt")
public class TopWearCnt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="neat_cnt", nullable = false)
    private int neatCnt = 0;

    @Column(name="short_sleeve_cnt", nullable = false)
    private int longSleeveCnt = 0;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weather_id", nullable = false)
    private Weather weather;

    @Builder
    public TopWearCnt(Weather weather) {
        this.weather = weather;
    }


}

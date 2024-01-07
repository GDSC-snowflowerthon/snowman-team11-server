package com.snowthon.snowman.domain.cnt;

import com.snowthon.snowman.domain.Weather;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "head_wear_cnt")
public class HeadWearCnt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name ="beanie_cnt", nullable = false)
    private int earmuffCnt = 0;

    @Column(name = "balaclava_cnt", nullable = false)
    private int balaclavaCnt = 0;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weather_id", nullable = false)
    private Weather weather;

    @Builder
    public HeadWearCnt(Weather weather) {
        this.weather = weather;
    }

}

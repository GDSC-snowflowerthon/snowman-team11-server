package com.snowthon.snowman.domain.cnt;

import com.snowthon.snowman.domain.Weather;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "neck_wear_cnt")
public class NeckWearCnt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "scarf_cnt", nullable = false)
    private int scarfCnt = 0;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weather_id", nullable = false)
    private Weather weather;
//
//    @Builder
//    public NeckWearCnt(Weather weather) {
//        this.weather = weather;
//    }
}

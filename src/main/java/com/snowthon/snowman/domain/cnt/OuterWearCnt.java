package com.snowthon.snowman.domain.cnt;

import com.snowthon.snowman.domain.Weather;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "outer_wear_cnt")
public class OuterWearCnt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "long_padding_cnt", nullable = false)
    private int longPaddingCnt = 0;

    @Column(name = "short_padding_cnt", nullable = false)
    private int shortPaddingCnt = 0;

    @Column(name = "coat_cnt", nullable = false)
    private int coatCnt = 0;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weather_id", nullable = false)
    private Weather weather;

    @Builder
    public OuterWearCnt(Weather weather) {
        this.weather = weather;
    }
}

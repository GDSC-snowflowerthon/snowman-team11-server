package com.snowthon.snowman.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "weathers")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="location", nullable = false)
    private String location;

    @Column(name="temperature", nullable = false)
    private int temperature;

    private LocalDateTime createdAt;

    @Builder
    public Weather(String location, int temperature) {
        this.location = location;
        this.temperature = temperature;
        this.createdAt = LocalDateTime.now();
    }
}

package com.snowthon.snowman.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "vote_status")
public class VoteStatus {

    /**
     * 유저의 투표 여부를 저장하는 테이블
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="weather_id", nullable = false)
    private Weather weather;

    @Builder
    public VoteStatus(User user, Weather weather) {
        this.user = user;
        this.weather = weather;
    }

}

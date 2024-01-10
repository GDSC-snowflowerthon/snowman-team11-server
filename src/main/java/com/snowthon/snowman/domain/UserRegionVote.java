package com.snowthon.snowman.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_region_vote")
public class UserRegionVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(name = "vote_time", nullable = false)
    private LocalDateTime voteTime;

    public static UserRegionVote create(User user, Region region) {
        UserRegionVote userRegionVote = new UserRegionVote();
        userRegionVote.user = user;
        userRegionVote.region = region;
        userRegionVote.voteTime = LocalDateTime.now();
        return userRegionVote;
    }
}

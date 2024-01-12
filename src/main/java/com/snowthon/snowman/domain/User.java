package com.snowthon.snowman.domain;


import com.snowthon.snowman.contrant.Constants;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "serial_id", nullable = false, unique = true)
    private Long serialId;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "is_login", columnDefinition = "TINYINT(1)")
    private Boolean isLogin;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;


    @Builder
    public User(Long serialId) {
        this.serialId = serialId;
        this.isLogin = true;
        this.createdAt = LocalDateTime.now();
        this.nickname = Constants.USER_NICKNAME_PREFIX + serialId;
    }

    public static User signUp(Long serialId) {
        return User.builder()
                .serialId(serialId)
                .build();
    }
}

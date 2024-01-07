package com.snowthon.snowman.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;


    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "is_login", columnDefinition = "TINYINT(1)")
    private Boolean isLogin;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;


    @Builder
    public User(String nickname, String email, String phoneNumber) {
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isLogin = true;
        this.createdAt = LocalDateTime.now();
    }

    public static User signUp(String nickname, String email, String phoneNumber) {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
    }
}

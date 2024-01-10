package com.snowthon.snowman.repository;


import com.snowthon.snowman.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySerialId(Long serialId);


    @Query("select u.id as id from User u where u.id = :id and u.isLogin = true")
    Optional<UserSecurityForm> findSecurityFormById(Long id);


    @Modifying(clearAutomatically = true)
    @Query("update User u set u.refreshToken = :refreshToken, u.isLogin = :isLogin where u.id = :id")
    void updateRefreshTokenAndLoginStatus(Long id, String refreshToken, Boolean isLogin);

    interface UserSecurityForm {
        Long getId();
        String getPassword();
    }

}

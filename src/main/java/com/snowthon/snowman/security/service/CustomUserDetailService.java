package com.snowthon.snowman.security.service;


import com.snowthon.snowman.domain.User;
import com.snowthon.snowman.dto.type.ErrorCode;
import com.snowthon.snowman.exception.CommonException;
import com.snowthon.snowman.repository.UserRepository;
import com.snowthon.snowman.security.info.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService {
    private final UserRepository userRepository;

    public UserDetails loadUserByEmailAndPhoneNumber(String email, String phoneNumber, String nickname) {
        return userRepository.findByEmailAndPhoneNumber(email, phoneNumber)
                .map(UserPrincipal::createByUserSecurityForm)
                .orElseGet(() -> {
                    User newUser = User.signUp(nickname, email, phoneNumber);
                    userRepository.save(newUser);
                    return UserPrincipal.createByUserId(newUser.getId());
                });
    }

    public UserDetails loadUserById(Long id) {
        UserRepository.UserSecurityForm user = userRepository.findSecurityFormById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LOGIN_USER));

        return UserPrincipal.createByUserSecurityForm(user);
    }
}

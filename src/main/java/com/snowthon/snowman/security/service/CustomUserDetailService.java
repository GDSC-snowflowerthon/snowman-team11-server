package com.snowthon.snowman.security.service;


import com.snowthon.snowman.dto.type.ErrorCode;
import com.snowthon.snowman.exception.CommonException;
import com.snowthon.snowman.repository.UserRepository;
import com.snowthon.snowman.security.info.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService {
    private final UserRepository userRepository;


    public UserDetails loadUserById(Long id) {
        UserRepository.UserSecurityForm user = userRepository.findSecurityFormById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LOGIN_USER));

        return UserPrincipal.createByUserSecurityForm(user);
    }


}

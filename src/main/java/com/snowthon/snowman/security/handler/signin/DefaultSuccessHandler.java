package com.snowthon.snowman.security.handler.signin;


import com.snowthon.snowman.dto.response.JwtTokenDto;
import com.snowthon.snowman.repository.UserRepository;
import com.snowthon.snowman.security.info.UserPrincipal;
import com.snowthon.snowman.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONValue;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("====DefaultSuccessHandler====");
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        JwtTokenDto tokenDto = jwtUtil.generateTokens(userPrincipal.getId(), userPrincipal.getRole());
        userRepository.updateRefreshTokenAndLoginStatus(userPrincipal.getId(), tokenDto.refreshToken(), true);


        setSuccessAppResponse(response, tokenDto);
    }

    private void setSuccessAppResponse(HttpServletResponse response, JwtTokenDto tokenDto) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", Map.of(
                        "accessToken", tokenDto.accessToken(),
                        "refreshToken", tokenDto.refreshToken()
                )
        );
        result.put("error", null);

        log.info("====user accessToken = {}", tokenDto.accessToken());
        response.getWriter().write(JSONValue.toJSONString(result));
    }
}

package com.snowthon.snowman.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowthon.snowman.dto.response.UserLoginDto;
import com.snowthon.snowman.security.handler.signin.DefaultFailureHandler;
import com.snowthon.snowman.security.handler.signin.DefaultSuccessHandler;
import com.snowthon.snowman.security.info.UserPrincipal;
import com.snowthon.snowman.security.service.CustomUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
public class CustomJsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    private static final String DEFAULT_LOGIN_PATH = "/api/auth/login";
    private final CustomUserDetailService customUserDetailService;
    private final ObjectMapper objectMapper;
    private final DefaultSuccessHandler defaultSuccessHandler;
    private final DefaultFailureHandler defaultFailureHandler;


    public CustomJsonUsernamePasswordAuthenticationFilter(
            ObjectMapper objectMapper,
            CustomUserDetailService customUserDetailService,
            DefaultSuccessHandler defaultSuccessHandler,
            DefaultFailureHandler defaultFailureHandler) {
        super(DEFAULT_LOGIN_PATH);
        this.objectMapper = objectMapper;
        this.customUserDetailService = customUserDetailService;
        this.defaultSuccessHandler = defaultSuccessHandler;
        this.defaultFailureHandler = defaultFailureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        UserLoginDto userLoginDto = objectMapper.readValue(request.getInputStream(), UserLoginDto.class);
        UserPrincipal userPrincipal = (UserPrincipal) customUserDetailService.loadUserByEmailAndPhoneNumber(userLoginDto.email(), userLoginDto.phoneNumber(), userLoginDto.nickname());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, userPrincipal.getAuthorities());

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        super.setAuthenticationSuccessHandler(defaultSuccessHandler);
    }

    @Override
    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        super.setAuthenticationFailureHandler(defaultFailureHandler);
    }
}

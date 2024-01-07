package com.snowthon.snowman.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowthon.snowman.contrant.Constants;
import com.snowthon.snowman.security.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import com.snowthon.snowman.security.filter.GlobalLoggerFilter;
import com.snowthon.snowman.security.filter.JwtAuthenticationFilter;
import com.snowthon.snowman.security.filter.JwtExceptionFilter;
import com.snowthon.snowman.security.handler.jwt.JwtAuthEntryPoint;
import com.snowthon.snowman.security.handler.signin.DefaultFailureHandler;
import com.snowthon.snowman.security.handler.signin.DefaultSuccessHandler;
import com.snowthon.snowman.security.handler.singout.CustomSignOutProcessHandler;
import com.snowthon.snowman.security.handler.singout.CustomSignOutResultHandler;
import com.snowthon.snowman.security.service.CustomUserDetailService;
import com.snowthon.snowman.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomSignOutProcessHandler customSignOutProcessHandler;
    private final CustomSignOutResultHandler customSignOutResultHandler;
    private final DefaultSuccessHandler defaultSuccessHandler;
    private final DefaultFailureHandler defaultFailureHandler;

    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    private final CustomUserDetailService customUserDetailService;
    private final JwtUtil jwtUtil;

    @Bean
    protected SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(registry ->
                        registry
                                .requestMatchers(Constants.NO_NEED_AUTH_URLS.toArray(String[]::new)).permitAll()
                                .anyRequest().authenticated()
                )
                .logout(configurer ->
                        configurer
                                .logoutUrl("/api/v1/auth/sign-out")
                                .addLogoutHandler(customSignOutProcessHandler)
                                .logoutSuccessHandler(customSignOutResultHandler)
                )
                .exceptionHandling(configurer ->
                        configurer
                                .authenticationEntryPoint(jwtAuthEntryPoint)
                )

                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtUtil, customUserDetailService),
                        LogoutFilter.class)
                .addFilterBefore(
                        new CustomJsonUsernamePasswordAuthenticationFilter(
                                new ObjectMapper(), customUserDetailService, defaultSuccessHandler, defaultFailureHandler),
                        JwtAuthenticationFilter.class)
                .addFilterBefore(
                        new JwtExceptionFilter(),
                        JwtAuthenticationFilter.class)
                .addFilterBefore(
                        new GlobalLoggerFilter(),
                        JwtExceptionFilter.class)

                .getOrBuild();
    }
}

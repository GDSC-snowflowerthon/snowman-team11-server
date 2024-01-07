package com.snowthon.snowman.contrant;

import java.util.List;


public class Constants {
    public static String USER_ID_CLAIM_NAME = "uid";
    public static String BEARER_PREFIX = "Bearer ";
    public static String AUTHORIZATION_HEADER = "Authorization";
    public static String USER_ROLE = "ROLE_USER";
    public static String GUEST_ROLE = "ROLE_GUEST";

    public static String ENDPOINT_PREFIX = "/api/v1";

    public static List<String> NO_NEED_AUTH_URLS = List.of(
            "/api-docs.html",
            "/api-docs/**",
            "/swagger-ui/**");
}

package com.snowthon.snowman.utility;

import com.snowthon.snowman.config.WebClientConfig;
import com.snowthon.snowman.dto.type.ErrorCode;
import com.snowthon.snowman.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebClientUtil {

    private final WebClientConfig webClientConfig;

    public <T> T get(String url, Class<T> responseDtoClass, Map<String, String> headers) {
        WebClient.RequestHeadersSpec<?> requestSpec = webClientConfig.webClient()
                .method(HttpMethod.GET)
                .uri(url);
        if (headers != null) {
            headers.forEach(requestSpec::header);
        }
        return requestSpec
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new CommonException(ErrorCode.MISSING_REQUEST_PARAMETER))
                )
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new CommonException(ErrorCode.INTERNAL_SERVER_ERROR))
                )
                .bodyToMono(responseDtoClass)
                .block();
    }

}

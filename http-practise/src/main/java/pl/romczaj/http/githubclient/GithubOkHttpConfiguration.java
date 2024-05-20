package pl.romczaj.http.githubclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.FeignException;
import feign.Logger;
import feign.Response;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Configuration
@Slf4j
class GithubOkHttpConfiguration {

    private static final String GITHUB = "github";

    @Bean
    GithubFeignInterface githubFeignClient(ObjectMapper objectMapper, GithubApiProperties githubApiProperties){

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .slowCallRateThreshold(1)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .slowCallDurationThreshold(Duration.ofSeconds(2))
                .permittedNumberOfCallsInHalfOpenState(3)
                .minimumNumberOfCalls(10)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)
                .slidingWindowSize(5)
                .recordExceptions(Exception.class, TimeoutException.class, FeignException.class)
                .build();

        CircuitBreaker customCircuitBreaker = CircuitBreaker.of(GITHUB, circuitBreakerConfig);

        customCircuitBreaker.getEventPublisher()
                .onEvent(event -> log.info("CircuitBreaker onEvent {}", event));

        RetryConfig retryConfig = RetryConfig.<Response>custom()
                .maxAttempts(4)
                .waitDuration(Duration.ofMillis(200))
                .retryExceptions(FeignException.class)
                .failAfterMaxAttempts(true)
                .build();

        RetryRegistry retryRegistry = RetryRegistry.of(retryConfig);
        Retry retry1 = retryRegistry.retry(GITHUB);


        retry1.getEventPublisher()
                .onRetry(event -> log.info("Retry onRetry {}", event));

        FeignDecorators decorators = FeignDecorators.builder()
              //  .withRateLimiter(rateLimiter)
                .withCircuitBreaker(customCircuitBreaker)
                .withRetry(retry1)
                .build();

        final Retryer retryer = new Retryer.Default(100L, 1_000L, 0);
        return Resilience4jFeign
                .builder(decorators)
                .client(new OkHttpClient())
                .retryer(retryer)
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .logger(new Slf4jLogger(GithubFeignInterface.class))
                .logLevel(Logger.Level.FULL)
                .target(GithubFeignInterface.class, githubApiProperties.getApiUrl());
    }


    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        mapper.registerModule(javaTimeModule);

        return mapper;
    }
}

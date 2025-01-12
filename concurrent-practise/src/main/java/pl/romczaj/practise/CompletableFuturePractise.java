package pl.romczaj.practise;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

import static java.util.concurrent.CompletableFuture.delayedExecutor;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.joining;

@Slf4j
public class CompletableFuturePractise {

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void allOf() {
        log.info("start allOf");
        String results = Stream.of(
                initDelayed("A", 1),
                initDelayed("B", 4),
                initDelayed("C", 3)
            )
            .map(CompletableFuture::join)
            .collect(joining(" "));

        log.info("finish allOf, results {}", results);
    }

    public void oneByOne() throws InterruptedException {
        log.info("start");
        executorService.submit(() -> processValue("A"));
        executorService.submit(() -> processValue("B"));
        executorService.submit(() -> processValue("C"));
        executorService.submit(() -> processValue("D"));
        executorService.submit(() -> processValue("E"));
        executorService.submit(() -> processValue("F"));

        Thread.sleep(6000L);
        log.info("finish ");
    }

    CompletableFuture<Void> processValue(String value) {
        return initValue(value)
            .thenApply(this::convertValue)
            .thenCompose(this::convertValueFuture)
            .thenComposeAsync(this::convertValueFuture)
            .thenApplyAsync(this::convertValue)
            .thenAccept(v -> log.info("Finish process {} to {}", value, v));
    }

    CompletableFuture<String> initValue(String value) {
        log.info("init {}", value);
        return CompletableFuture.supplyAsync(() -> value, executorService);
    }

    CompletableFuture<String> convertValueFuture(String value) {
        String convertedValue = "_cvf_" + value;
        log.info("Convert value future to: {}", convertedValue);
        return CompletableFuture.supplyAsync(() -> convertedValue);
    }

    String convertValue(String value) {
        String convertedValue = "_cv_" + value;
        log.info("Convert value to: {}", convertedValue);
        return convertedValue;
    }

    CompletableFuture<String> initDelayed(String value, Integer delayInSeconds) {
        return CompletableFuture.supplyAsync(() -> value, delayedExecutor(delayInSeconds, SECONDS));
    }
}

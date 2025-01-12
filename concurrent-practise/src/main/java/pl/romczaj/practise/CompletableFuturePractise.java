package pl.romczaj.practise;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
public class CompletableFuturePractise {

    ExecutorService executorService = Executors.newFixedThreadPool(10);
    Executor delayed = CompletableFuture.delayedExecutor(5L, SECONDS);

    public void convertChain() throws InterruptedException, ExecutionException {
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
}

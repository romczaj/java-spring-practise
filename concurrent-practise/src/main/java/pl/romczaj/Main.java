package pl.romczaj;

import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import pl.romczaj.practise.CompletableFuturePractise;

@Slf4j
public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        log.info("main");
        CompletableFuturePractise completableFuturePractise = new CompletableFuturePractise();
        completableFuturePractise.convertChain();
    }
}
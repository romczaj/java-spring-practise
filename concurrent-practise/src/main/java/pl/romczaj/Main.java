package pl.romczaj;

import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import pl.romczaj.practise.CompletableFuturePractise;
import pl.romczaj.practise.StreamPractise;

@Slf4j
public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        log.info("main");
        CompletableFuturePractise completableFuturePractise = new CompletableFuturePractise();
     //   completableFuturePractise.convertChain();

        StreamPractise streamPractise = new StreamPractise();
        streamPractise.process();
    }
}
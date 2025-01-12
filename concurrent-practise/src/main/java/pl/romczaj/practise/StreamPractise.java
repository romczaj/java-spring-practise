package pl.romczaj.practise;

import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class StreamPractise {

    public void process() {

        Stream.of(1, 2, 3, 4, 5, 6)
            .map(this::powerValue)
            .filter(n -> n > 3)
            .map(this::doubleValue)
            .filter(n -> n > 10)
            .findFirst();

        System.out.println("Finish");
    }

    Integer powerValue(Integer a) {
        System.out.println("Power " + a);
        return a * a;
    }

    Integer doubleValue(Integer a) {
        System.out.println("Double " + a);
        return a + a;
    }
}

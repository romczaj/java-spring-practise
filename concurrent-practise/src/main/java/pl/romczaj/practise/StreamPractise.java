package pl.romczaj.practise;

import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class StreamPractise {

    public void process() {

        Stream.of(1, 2, 3, 4, 5, 6)
            .map(this::multiply)
            .filter(n -> n > 3)
            .map(this::add)
            .filter(n -> n > 10)
            .findFirst();

        System.out.println("Finish");
    }

    Integer multiply(Integer a) {
        System.out.println("Multiplying " + a);
        return a * a;
    }

    Integer add(Integer a) {
        System.out.println("Adding " + a);
        return a + a;
    }
}

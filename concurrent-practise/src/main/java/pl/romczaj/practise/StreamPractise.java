package pl.romczaj.practise;

import java.util.List;
import java.util.stream.Stream;
import lombok.With;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StreamPractise {

    public void process() {

        List<ProcessObject> list = Stream.of(
                ProcessObject.init(1),
                ProcessObject.init(2),
                ProcessObject.init(3),
                ProcessObject.init(4),
                ProcessObject.init(5),
                ProcessObject.init(6))
            .map(this::powerValue)
            .filter(n -> n.actualValue() > 3)
            .map(this::doubleValue)
            .filter(n -> n.actualValue() > 10)
            .limit(3)
            .toList();

        log.info("Result {}", list);
    }

    ProcessObject powerValue(ProcessObject processObject) {
        Integer poweredValue = processObject.actualValue() * processObject.actualValue();
        ProcessObject poweredObject = processObject.withActualValue(poweredValue);
        log.info("Power value to {}", poweredObject);
        return poweredObject;
    }

    ProcessObject doubleValue(ProcessObject a) {
        Integer doubledValue = a.actualValue() * 2;
        ProcessObject doubledObject = a.withActualValue(doubledValue);
        log.info("Double value to {}", doubledObject);
        return doubledObject;
    }

    record ProcessObject(
        Integer initValue,
        @With
        Integer actualValue
    ) {

        static ProcessObject init(Integer value) {
            return new ProcessObject(value, value);
        }
    }
}

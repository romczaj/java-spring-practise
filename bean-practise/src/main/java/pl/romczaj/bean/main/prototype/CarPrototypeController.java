package pl.romczaj.bean.main.prototype;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.romczaj.bean.meh.app.MehBean;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prototype")
@Slf4j
class CarPrototypeController {

    private final ApplicationContext applicationContext;
    private final MehBean mehBean;

    @GetMapping("/start-engine")
    void startEngine(@RequestParam CarType carType) {
        log.info("carPrototype {}", carPrototype().getIdBean());

        Car bean = applicationContext.getBean(carType.getBeanName(), Car.class);
        bean.startEngine();
    }

    @Lookup
    CarPrototype carPrototype() {
        return null;
    }


    @Lookup
    Car getCar(String carType) {
        return null;
    }
}

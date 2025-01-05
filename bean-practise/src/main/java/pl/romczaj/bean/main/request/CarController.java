package pl.romczaj.bean.main.request;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@RestController
@Slf4j
@RequestMapping("/request")
class CarController {

    private final Random random = new Random();
    private final CarService carService;
    private final OrderRequest orderRequest;

    CarController(CarService carService, OrderRequest orderRequest) {
        this.carService = carService;
        this.orderRequest = orderRequest;
        log.info("Create CarController");
    }

    @GetMapping("/{id}")
    CarResponse getCar(@PathVariable Long id) {
        orderRequest.setCarId(id);
        log.info("controller {}", orderRequest);
        delay(random.nextInt(3000));
        return carService.getCar();
    }

    private void delay(Integer miliseconds) {
        try {
            MILLISECONDS.sleep(miliseconds);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}

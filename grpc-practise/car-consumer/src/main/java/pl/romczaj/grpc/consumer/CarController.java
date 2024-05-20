package pl.romczaj.grpc.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class CarController {

    private final CarConsumer carConsumer;

    @GetMapping("/invoke-async")
    void invokeAsync() {
        carConsumer.invokeAsync();
    }

    @GetMapping("/invoke-blocking")
    CarRestResponse invokeBlocking(@RequestParam Integer id) {
        return carConsumer.invokeBlocking(id);
    }
}

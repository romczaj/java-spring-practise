package pl.romczaj.heap.controller;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class InvokeController {

    private final CarService carService;

    public InvokeController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/car")
    public void car() {
      carService.car();
    }

    @GetMapping("/bike")
    public void bike() {
        bikeService().run();
    }


    @GetMapping("/invoke-heap-overflow")
    public void invokeHeapOverflow() {
        System.out.println("sdsf");
        fillMemory();
    }

    @Lookup
    public BikeService bikeService() {
        return null;
    }

    public static void fillMemory() {
        List<ByteObject> memoryList = new ArrayList<>();

        while (true) {
            byte[] memoryChunk = new byte[1024 * 1024]; // 1 MB

            ByteObject byteObject = new ByteObject("objectName", memoryChunk);
            memoryList.add(byteObject);
        }
    }
}

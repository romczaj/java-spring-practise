package pl.romczaj.security.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

    @GetMapping("/user/cars")
    public ResponseEntity<List<CarResponse>> userCars() {
        return ResponseEntity.ok(
          List.of(
              new CarResponse(1L, "BMW 1"),
              new CarResponse(2L, "BMW 2")
          )
        );
    }

    @GetMapping("/admin/cars")
    public ResponseEntity<List<CarResponse>> adminCars() {
        return ResponseEntity.ok(
            List.of(
                new CarResponse(1L, "Mercedes 1"),
                new CarResponse(2L, "Mercedes 2")
            )
        );
    }
}

package pl.romczaj.grpc.carprovider;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
class CarDao {

    private final Map<Integer, Car> database;

    CarDao() {
        HashMap<Integer, Car> carMap = new HashMap<>();
        carMap.put(1, new Car(1, "BMW"));
        carMap.put(2, new Car(2, "TOYOTA"));
        carMap.put(3, new Car(3, "FIAT"));
        this.database = carMap;
    }

    Optional<Car> findById(Integer id){
        return Optional.ofNullable(database.get(id));
    }

    Car getById(Integer id){
        return findById(id).orElseThrow(() -> NotFoundException.carNotFound(id));
    }

}

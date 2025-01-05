package pl.romczaj.bean.main.prototype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static pl.romczaj.bean.main.prototype.ApplicationConstant.TRUCK_BEAN;

@Component(TRUCK_BEAN)
class Truck implements Car {

    private static final Logger log = LoggerFactory.getLogger(Truck.class);

    @Override
    public void startEngine() {
        log.info("Truck start engine");
    }
}

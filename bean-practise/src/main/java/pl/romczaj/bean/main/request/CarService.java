package pl.romczaj.bean.main.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.romczaj.bean.main.prototype.CarPrototype;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Slf4j
@Scope(value = SCOPE_PROTOTYPE)
class CarService {

    private final OrderRequest orderRequest;
    private final CarPrototype carPrototype;

    CarService(OrderRequest orderRequest, CarPrototype carPrototype) {
        this.orderRequest = orderRequest;
        this.carPrototype = carPrototype;
        log.info("Create CarService");
    }

    CarResponse getCar() {
        log.info("service {}, carPrototype {}", orderRequest, carPrototype.getIdBean());
        return new CarResponse(orderRequest.getCarId());
    }
}

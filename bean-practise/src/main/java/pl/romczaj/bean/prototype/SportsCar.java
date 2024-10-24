package pl.romczaj.bean.prototype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static pl.romczaj.bean.prototype.ApplicationConstant.SPORT_CAR_BEAN;

@Component(SPORT_CAR_BEAN)
class SportsCar implements Car{

    private static final Logger log = LoggerFactory.getLogger(SportsCar.class);

    @Override
    public void startEngine() {
        log.info("SportsCar start engine");
    }


}

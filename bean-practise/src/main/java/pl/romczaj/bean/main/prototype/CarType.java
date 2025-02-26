package pl.romczaj.bean.main.prototype;

import static pl.romczaj.bean.main.prototype.ApplicationConstant.SPORT_CAR_BEAN;
import static pl.romczaj.bean.main.prototype.ApplicationConstant.TRUCK_BEAN;

enum CarType {
    SPORT_CAR(SPORT_CAR_BEAN),
    TRUCK(TRUCK_BEAN);

    private final String beanName;

    CarType(String beanName) {
        this.beanName = beanName;
    }

    String getBeanName() {
        return beanName;
    }
}

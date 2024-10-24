package pl.romczaj.bean.prototype;

import java.util.UUID;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(value = SCOPE_PROTOTYPE)
@Slf4j
@Getter
public class CarPrototype {

    private final UUID idBean;

    CarPrototype() {
        this.idBean = UUID.randomUUID();
        log.info("Creating CarPrototype with id {}", idBean);
    }
}

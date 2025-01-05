package pl.romczaj.bean.meh.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MehBean {

    public void process(){
        log.info("process");
    }
}

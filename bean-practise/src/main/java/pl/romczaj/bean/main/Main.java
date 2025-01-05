package pl.romczaj.bean.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import pl.romczaj.bean.meh.MehConfiguration;

@SpringBootApplication
@ComponentScan(basePackages = {
    "pl.romczaj.bean.main.prototype", "pl.romczaj.bean.main.request",
})
@Import(MehConfiguration.class)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
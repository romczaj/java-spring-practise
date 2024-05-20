package pl.romczaj.multidomain;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class IntegrationTestConfiguration {

    @Bean
    TestIntegrationRepository personTestIntegrationRepository(TestEntityManager testEntityManager){
        return new TestIntegrationRepository(testEntityManager);
    }

}

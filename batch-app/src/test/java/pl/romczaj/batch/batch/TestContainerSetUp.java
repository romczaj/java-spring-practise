package pl.romczaj.batch.batch;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
@TestConfiguration
public class TestContainerSetUp {
    static final String DB_USERNAME = "admin";
    static final String DB_PASSWORD = "password";

    public static PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("integration-tests-db")
            .withUsername(DB_USERNAME)
            .withPassword(DB_PASSWORD);

    static {
        POSTGRE_SQL_CONTAINER.start();
        log.info("Postgresql container started");

        String jdbcUrl = POSTGRE_SQL_CONTAINER.getJdbcUrl();
        System.setProperty("spring.datasource.url", jdbcUrl);
        System.setProperty("spring.datasource.username", DB_USERNAME);
        System.setProperty("spring.datasource.password", DB_PASSWORD);
    }
}

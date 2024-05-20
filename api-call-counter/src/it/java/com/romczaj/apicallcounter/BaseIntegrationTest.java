package com.romczaj.apicallcounter;

import com.romczaj.apicallcounter.callcounter.CallCounterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@AutoConfigureMockMvc
@SpringBootTest(classes = Main.class)
@RunWith(SpringRunner.class)
@ActiveProfiles(value = "it")
public abstract class BaseIntegrationTest {

    @Autowired
    protected CallCounterRepository callCounterRepository;

    @BeforeEach
    void clearDatabase() {
        callCounterRepository.deleteAll();
    }
}

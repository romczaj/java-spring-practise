package pl.romczaj.bean.main.request;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldReturnCorrectIds() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

       IntStream.range(1, 11)
            .boxed()
            .map(id -> buildRequest(id, executorService))
            .toList()
            .forEach(CompletableFuture::join);

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

    }

    private CompletableFuture<ResultActions> buildRequest(Integer number, ExecutorService executorService) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return mockMvc.perform(get("/request/" + number))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(number));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, executorService);
    }
}
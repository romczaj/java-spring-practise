package pl.romczaj.grpc.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcCarConsumer {
    public static void main(String[] args) {
        SpringApplication.run(GrpcCarConsumer.class, args);
    }
}
package pl.romczaj.grpc.carprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcCarProvider {
    public static void main(String[] args) {
        SpringApplication.run(GrpcCarProvider.class, args);
    }
}
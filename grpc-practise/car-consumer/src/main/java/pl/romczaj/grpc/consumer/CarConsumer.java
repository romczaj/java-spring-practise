package pl.romczaj.grpc.consumer;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import pl.romczaj.grpc.api.CarRequest;
import pl.romczaj.grpc.api.CarResponse;
import pl.romczaj.grpc.api.CarServiceGrpc;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Component
@Slf4j
class CarConsumer {

    public CarConsumer(@GrpcClient("car") CarServiceGrpc.CarServiceStub carAsyncStub,
                       @GrpcClient("car") CarServiceGrpc.CarServiceBlockingStub carServiceBlockingStub) {
        this.carAsyncStub = carAsyncStub;
        this.carServiceBlockingStub = carServiceBlockingStub;
    }

    private final CarServiceGrpc.CarServiceStub carAsyncStub;
    private final CarServiceGrpc.CarServiceBlockingStub carServiceBlockingStub;

    CarRestResponse invokeBlocking(Integer id){
        CarRequest carRequest = CarRequest.newBuilder().setId(id).build();
        CarResponse carResponse = carServiceBlockingStub
                .withDeadlineAfter(5, TimeUnit.SECONDS).getByName(carRequest);
        log.info("Retrieve blocking response {}", carResponse);
        return CarRestResponse.from(carResponse);
    }

    void invokeAsync(){
        final StreamObserver<CarResponse> responseObserver = new StreamObserver<>() {

            @Override
            public void onNext(CarResponse carResponse) {
                log.info("Retrieve async response {}", carResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("Async Error occured", throwable);
            }

            @Override
            public void onCompleted() {
                log.info("Async response finished");
            }
        };
        StreamObserver<CarRequest> request = carAsyncStub.streamCar(responseObserver);

        Stream.of(1, 2, 3)
                .map(CarRequest.newBuilder()::setId)
                .map(CarRequest.Builder::build)
                .forEach(request::onNext);
        request.onCompleted();
    }
}

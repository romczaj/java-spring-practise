package pl.romczaj.grpc.carprovider;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import pl.romczaj.grpc.api.CarRequest;
import pl.romczaj.grpc.api.CarResponse;
import pl.romczaj.grpc.api.CarServiceGrpc;


@GrpcService
@RequiredArgsConstructor
class GrpcCarService extends CarServiceGrpc.CarServiceImplBase {

    private final CarDao carDao;

    @Override
    public StreamObserver<CarRequest> streamCar(StreamObserver<CarResponse> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(CarRequest value) {
                carDao.findById(value.getId())
                        .map(Car::toGrpcResponse)
                        .ifPresent(responseObserver::onNext);
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void getByName(CarRequest request, StreamObserver<CarResponse> responseObserver) {
//        carDao.findById(request.getId())
//                        .ifPresent(car -> {
//                            responseObserver.onNext(car.toGrpcResponse());
//                            responseObserver.onCompleted();
//                        });

        Car car = carDao.getById(request.getId());
        responseObserver.onNext(car.toGrpcResponse());
        responseObserver.onCompleted();
    }
}

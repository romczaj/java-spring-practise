package pl.romczaj.grpc.carprovider;

import io.grpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class AppExceptionHandler {

    @GrpcExceptionHandler(NotFoundException.class)
    public Status handleNotFoundException(NotFoundException e) {
        return Status.NOT_FOUND.withDescription(e.getMessage());
    }
}

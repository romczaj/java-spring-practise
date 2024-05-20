package pl.romczaj.grpc.carprovider;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }

    static NotFoundException carNotFound(Integer id){
        return new NotFoundException(String.format("Car id %s not found", id));
    }
}

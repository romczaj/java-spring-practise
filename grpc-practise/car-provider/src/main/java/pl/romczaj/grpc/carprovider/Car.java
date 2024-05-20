package pl.romczaj.grpc.carprovider;

import pl.romczaj.grpc.api.CarResponse;

record Car(
        Integer id,
        String name
) {

    CarResponse toGrpcResponse(){
        return CarResponse.newBuilder()
                .setId(id)
                .setName(name)
                .build();
    }
}

package pl.romczaj.grpc.consumer;

import pl.romczaj.grpc.api.CarResponse;

record CarRestResponse(Integer id, String name) {

    static CarRestResponse from(CarResponse carResponse){
        return new CarRestResponse(carResponse.getId(), carResponse.getName());
    }
}

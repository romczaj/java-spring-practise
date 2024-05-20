package pl.romczaj.heap.controller;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CarService {

    public CarService() {
        System.out.println("Creating CarService");
    }

    public void car(){
        System.out.println("car method");

    }
}

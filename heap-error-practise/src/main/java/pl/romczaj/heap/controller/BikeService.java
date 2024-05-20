package pl.romczaj.heap.controller;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BikeService {

    public BikeService() {
        System.out.println("Creating BikeService");
    }

    public void run(){
        System.out.println("BikeService run");
    }
}

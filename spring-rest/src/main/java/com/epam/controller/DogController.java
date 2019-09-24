package com.epam.controller;

import com.epam.model.Dog;
import com.epam.service.DogService;
import com.epam.service.TransactionProxyFactoryBean;
import com.epam.service.TransactionalProxy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/dog", produces = MediaType.APPLICATION_JSON_VALUE)
public class DogController {

    // TODO: 8/1/19 mockmvc and restassured tests, valid annotations and test that controller validates
    // TODO: 8/1/19 provide dependency by tomcat instead of EL import
    // TODO: 8/28/2019  public cache vs private cache
    // TODO: 8/29/2019 make custom exception
    // TODO: 9/19/2019 anonymous bean, do not declare beans if we do not need it why dont's use component scan?

    private DogService dogService;

    public DogController(TransactionalProxy transactionalProxy) {
        this.dogService =new TransactionProxyFactoryBean(transactionalProxy).getObject();
    }

    @PostMapping
    public Dog createDog(@RequestBody Dog dog) {
        return dogService.createDog(dog);
    }

    @GetMapping("/{id}")
    public Dog getDog(@PathVariable UUID id) {
        return dogService.getDog(id);
    }

    @PutMapping("/{id}")
    public Dog updateDog(@PathVariable UUID id, @RequestBody Dog dog) {
        return dogService.updateDog(id, dog);
    }

    @DeleteMapping("/{id}")
    public void deleteDog(@PathVariable UUID id) {
        dogService.deleteDog(id);
    }
}

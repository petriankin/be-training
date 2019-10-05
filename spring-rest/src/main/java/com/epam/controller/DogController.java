package com.epam.controller;

import com.epam.model.Dog;
import com.epam.service.DogService;
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
    // TODO: 9/25/2019 inject factory bean to controller make id like it's a service'
    // TODO: 9/25/2019 are annotations inherited?
    // TODO: 9/25/2019 if in transactional read only transactional
    // TODO: 9/25/2019  the last point at step 7 is incorrect
    // TODO: 9/25/2019 propagation level

    private DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
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

package com.epam.controller;

import com.epam.model.Dog;
import com.epam.service.DogService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/dog", produces = MediaType.APPLICATION_JSON_VALUE)
public class DogController {

    // TODO: 8/1/19 mockmvc and restassured tests
    // TODO: 8/1/19 provide dependency by tomcat instead of EL import
    // TODO: 8/1/19 valid annotations and test that controller validates
    // TODO: 8/28/2019  public cache vs private cache

    private DogService dogService;

    @PostMapping
    public Dog createDog(@RequestBody Dog dog) throws SQLException {
        return dogService.createDog(dog);
    }

    @GetMapping("/{id}")
    public Dog getDog(@PathVariable UUID id) throws SQLException {
        return dogService.getDog(id);
    }

    @PutMapping("/{id}")
    public Dog updateDog(@PathVariable UUID id, @RequestBody Dog dog) throws SQLException {
        return dogService.updateDog(id, dog);
    }

    @DeleteMapping("/{id}")
    public void deleteDog(@PathVariable UUID id) throws SQLException {
        dogService.deleteDog(id);
    }
}

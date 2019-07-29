package com.epam.controller;

import com.epam.dao.JdbcDogDao;
import com.epam.model.Dog;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/dog", produces = MediaType.APPLICATION_JSON_VALUE)
public class DogController {

    private JdbcDogDao dao;

    public DogController(JdbcDogDao dao) {
        this.dao = dao;
    }

    @PostMapping
    public Dog createDog(@RequestBody Dog dog) {
        return dao.createDog(dog);
    }

    @GetMapping("/{id}")
    public Dog getDog(@PathVariable UUID id) {
        return dao.getDog(id);
    }

    @PutMapping("/{id}")
    public Dog updateDog(@PathVariable UUID id, @RequestBody Dog dog) {
        return dao.updateDog(id, dog);
    }

    @DeleteMapping("/{id}")
    public void deleteDog(@PathVariable UUID id) {
        dao.deleteDog(id);
    }

}

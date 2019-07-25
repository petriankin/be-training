package com.epam.controller;

import com.epam.model.Dog;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value = "/dog", produces = MediaType.APPLICATION_JSON_VALUE)
public class DogController {

    private static ConcurrentHashMap<UUID, Dog> dogs = new ConcurrentHashMap<>();

    @PostMapping
    public Dog createDog(@RequestBody Dog dog) {
        UUID id = UUID.randomUUID();
        dog.setId(id);
        dogs.put(id, dog);
        return dog;
    }

    @GetMapping("/{id}")
    public Dog getDog(@PathVariable UUID id) {
        return dogs.get(id);
    }

    @PutMapping("/{id}")
    public Dog updateDog(@PathVariable UUID id, @RequestBody Dog dog) {
        return dogs.put(id, dog);
    }

    @DeleteMapping("/{id}")
    public void deleteDog(@PathVariable UUID id) {
        dogs.remove(id);
    }

}

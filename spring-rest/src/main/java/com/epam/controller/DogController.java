package com.epam.controller;

import com.epam.model.Dog;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/dog", produces = MediaType.APPLICATION_JSON_VALUE)
public class DogController {

    private static Map<UUID, Dog> dogs = new HashMap<>();

    @PostMapping
    public Dog createDog(@RequestBody Dog dog) {
        UUID id = UUID.randomUUID();
        dog.setId(id);
        dogs.put(id, dog);
        return dog;
    }

    @GetMapping("/{id}")
    public Dog getDog(@PathVariable UUID id) {
        return dogs.getOrDefault(id, new Dog());
    }

    @PutMapping("/{id}")
    public Dog updateDog(@PathVariable UUID id, @RequestBody Dog dog) {
        return dogs.put(id, dog);
    }

    @DeleteMapping("/{id}")
    public void deleteDog(@PathVariable UUID id) {
        dogs.put(id, null);
    }

}

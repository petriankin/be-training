package com.epam.controller;

import com.epam.dao.DogDao;
import com.epam.model.Dog;
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
    // TODO: 8/1/19 valid annotations and test thaat controlle validates

    /**
     * public cache vs private cache
     *
     * */

    private DogDao dao;

    @PostMapping
    public Dog createDog(@RequestBody Dog dog) throws SQLException {
        dog.setId(UUID.randomUUID());
        return dao.createDog(dog);
    }

    @GetMapping("/{id}")
    public Dog getDog(@PathVariable UUID id) throws SQLException {
        return dao.getDog(id);
    }

    @PutMapping("/{id}")
    public Dog updateDog(@PathVariable UUID id, @RequestBody Dog dog) throws SQLException {
        return dao.updateDog(id, dog);
    }

    @DeleteMapping("/{id}")
    public void deleteDog(@PathVariable UUID id) throws SQLException {
        dao.deleteDog(id);
    }

}

package com.epam.service;

import com.epam.dao.DogDao;
import com.epam.model.Dog;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.UUID;

@AllArgsConstructor
public class DogService {

    private DogDao dogDao;

    public Dog createDog(Dog dog) throws SQLException {
        dog.setId(UUID.randomUUID());
        return dogDao.createDog(dog);
    }

    public Dog getDog(UUID id) throws SQLException {
        return dogDao.getDog(id);
    }

    public Dog updateDog(UUID id, Dog dog) throws SQLException {
        return dogDao.updateDog(id, dog);
    }

    public void deleteDog(UUID id) throws SQLException {
        dogDao.deleteDog(id);
    }
}

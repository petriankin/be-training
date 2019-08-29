package com.epam.service;

import com.epam.dao.DogDao;
import com.epam.model.Dog;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DogService {

    private DogDao dogDao;

    public Dog createDog(Dog dog) {
        dog.setId(UUID.randomUUID());
        return dogDao.createDog(dog);
    }

    public Dog getDog(UUID id) {
        return dogDao.getDog(id);
    }

    public Dog updateDog(UUID id, Dog dog) {
        return dogDao.updateDog(id, dog);
    }

    public void deleteDog(UUID id) {
        dogDao.deleteDog(id);
    }
}

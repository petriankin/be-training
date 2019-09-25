package com.epam.service;

import com.epam.aop.LogMethodInvocation;
import com.epam.dao.DogDao;
import com.epam.model.Dog;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DogServiceImpl implements DogService {

    private DogDao dogDao;

    @Override
    @LogMethodInvocation
    public Dog createDog(Dog dog) {
        dog.setId(UUID.randomUUID());
        return dogDao.createDog(dog);
    }

    @Override
    public Dog getDog(UUID id) {
        return dogDao.getDog(id);
    }

    @Override
    public Dog updateDog(UUID id, Dog dog) {
        return dogDao.updateDog(id, dog);
    }

    @Override
    public void deleteDog(UUID id) {
        dogDao.deleteDog(id);
    }
}

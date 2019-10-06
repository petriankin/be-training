package com.epam.service.impl;

import com.epam.annotation.CustomTransactional;
import com.epam.annotation.LogMethodInvocation;
import com.epam.dao.DogDao;
import com.epam.model.Dog;
import com.epam.service.DogService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class DogServiceImpl implements DogService {

    private DogDao dogDao;

    @Override
    @LogMethodInvocation
    @CustomTransactional
    public Dog createDog(Dog dog) {
        dog.setId(UUID.randomUUID());
        Dog created = dogDao.createDog(dog);
        return dogDao.getDog(created.getId());
    }

    @Override
    public Dog getDog(UUID id) {
        return dogDao.getDog(id);
    }

    @Override
    @CustomTransactional
    public Dog updateDog(UUID id, Dog dog) {
        dogDao.updateDog(id, dog);
        return dogDao.getDog(id);
    }

    @Override
    @CustomTransactional
    public void deleteDog(UUID id) {
        dogDao.deleteDog(id);
    }
}

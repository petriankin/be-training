package com.epam.service.impl;

import com.epam.dao.JdbcConnectionHolder;
import com.epam.model.Dog;
import com.epam.service.DogService;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class TransactionalDogServiceImpl implements DogService {

    private DogService dogService;
    protected JdbcConnectionHolder connectionHolder;

    public Dog createDog(Dog dog) {
        Dog createdDog = null;
        try {
            connectionHolder.startTransaction();
            createdDog = dogService.createDog(dog);
            connectionHolder.commitTransaction();
        } catch (Exception e) {
            connectionHolder.rollbackTransaction();
        } finally {
            connectionHolder.closeConnection();
        }

        return createdDog;
    }

    public Dog getDog(UUID id) {
        Dog dog = null;
        try {
            connectionHolder.startTransaction();
            dog = dogService.getDog(id);
            connectionHolder.commitTransaction();
        } catch (Exception e) {
            connectionHolder.rollbackTransaction();
        } finally {
            connectionHolder.closeConnection();
        }
        return dog;
    }

    public Dog updateDog(UUID id, Dog dog) {
        Dog updated;
        try {
            connectionHolder.startTransaction();
            updated = dogService.updateDog(id, dog);
            connectionHolder.commitTransaction();
        } catch (Exception e) {
            connectionHolder.rollbackTransaction();
            throw new RuntimeException(e);
        }
        return updated;
    }

    public void deleteDog(UUID id) {
        try {
            connectionHolder.startTransaction();
            dogService.deleteDog(id);
            connectionHolder.commitTransaction();
        } catch (Exception e) {
            connectionHolder.rollbackTransaction();
            throw new RuntimeException(e);
        }
    }
}
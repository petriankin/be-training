package com.epam.service;

import com.epam.JdbcConnectionHolder;
import com.epam.dao.DogDao;
import com.epam.model.Dog;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class TransactionalDogService {

    private DogService dogService;
    protected JdbcConnectionHolder connectionHolder;

    public Dog createDog(Dog dog) {
        Dog createdDog = null;
        try (Connection connection = connectionHolder.getConnectionWithNoAutoCommit()) {
            createdDog = dogService.createDog(dog);
            connectionHolder.commit();
        } catch (SQLException e) {

        }
        return createdDog;
    }

    public Dog getDog(UUID id) {
        Dog dog = null;
        try (Connection connection = connectionHolder.getConnectionWithNoAutoCommit()) {
            dog = dogService.getDog(id);
        } catch (SQLException e) {
            connectionHolder.rollback();
        }
        return dog;
    }

    public Dog updateDog(UUID id, Dog dog) {
        Dog dog1 = null;
        try (Connection connection = connectionHolder.getConnectionWithNoAutoCommit()) {
            dog1 = dogService.updateDog(id, dog);
        } catch (SQLException e) {
            connectionHolder.rollback();
        }
        return dog1;
    }

    public void deleteDog(UUID id) {
        try (Connection connection = connectionHolder.getConnectionWithNoAutoCommit()) {
            dogService.deleteDog(id);
        } catch (SQLException e) {
            connectionHolder.rollback();
        }
    }

}

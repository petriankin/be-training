package com.epam.service;

import com.epam.JdbcConnectionHolder;
import com.epam.model.Dog;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
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
        }
        finally {
            connectionHolder.closeConnection();
        }

        return createdDog;
    }

    public Dog getDog(UUID id) {
        Dog dog = null;
        try  {
            connectionHolder.startTransaction();
            dog = dogService.getDog(id);
            connectionHolder.commitTransaction();
        } catch (Exception e) {
            connectionHolder.rollbackTransaction();
        }
        finally {
            connectionHolder.closeConnection();
        }
        return dog;
    }

    public Dog updateDog(UUID id, Dog dog) {
        Dog dog1 = null;
        try (Connection connection = connectionHolder.getConnectionWithNoAutoCommit()) {
            dog1 = dogService.updateDog(id, dog);
        } catch (SQLException e) {
            connectionHolder.rollbackTransaction();
            throw new RuntimeException(e);
        }
        return dog1;
    }

    public void deleteDog(UUID id) {
        try (Connection connection = connectionHolder.getConnectionWithNoAutoCommit()) {
            dogService.deleteDog(id);
        } catch (SQLException e) {
            connectionHolder.rollbackTransaction();
            throw new RuntimeException(e);
        }
    }

}

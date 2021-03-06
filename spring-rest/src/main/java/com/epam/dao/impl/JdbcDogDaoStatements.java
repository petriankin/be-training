package com.epam.dao.impl;

import com.epam.dao.DogDao;
import com.epam.dao.JdbcConnectionHolder;
import com.epam.model.Dog;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

@AllArgsConstructor
public class JdbcDogDaoStatements implements DogDao {

    private JdbcConnectionHolder connectionHolder;

    @Override
    public Dog createDog(Dog dog) {
        try (Connection connection = connectionHolder.getConnectionWithAutoCommit()) {
            final Statement statement = connection.createStatement();
            final int createdRows = statement.executeUpdate(String.format(
                    "INSERT INTO dog (id, name, date_of_birth, height, weight) VALUES ('%s', '%s', '%s', '%d', '%d');",
                    dog.getId(), dog.getName(), dog.getDateOfBirth(), dog.getHeight(), dog.getWeight()
            ));

            if (createdRows == 0) {
                throw new RuntimeException("Failed to create new dog");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return dog;
    }

    @Override
    public Dog getDog(UUID id) {
        Dog dog;

        try (Connection connection = connectionHolder.getConnectionWithAutoCommit()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(String.format(
                    "select * from dog where id = '%s'", id
            ));

            dog = Dog.mapDog(resultSet);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return dog;
    }

    @Override
    public Dog updateDog(UUID id, Dog dog) {
        try (Connection connection = connectionHolder.getConnectionWithAutoCommit()) {
            final Statement statement = connection.createStatement();
            final int updatedRows = statement.executeUpdate(String.format(
                    "update dog set NAME = '%s', DATE_OF_BIRTH = '%s', HEIGHT = '%d', WEIGHT = '%d' where id = '%s'",
                    dog.getName(), dog.getDateOfBirth(), dog.getHeight(), dog.getWeight(), id
            ));

            if (updatedRows == 0) {
                throw new RuntimeException(String.format("Failed to update dog with id %s", dog.getId()));
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return dog;
    }

    @Override
    public void deleteDog(UUID id) {
        try (Connection connection = connectionHolder.getConnectionWithAutoCommit()) {
            final Statement statement = connection.createStatement();
            final int deletedRows = statement.executeUpdate(
                    String.format("delete from dog where id = '%s'", id)
            );

            if (deletedRows == 0) {
                throw new RuntimeException("Failed to delete dog");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}

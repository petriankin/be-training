package com.epam.dao.impl;

import com.epam.JdbcConnectionHolder;
import com.epam.dao.DogDao;
import com.epam.dao.JdbcDogDao;
import com.epam.model.Dog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class JdbcDogDaoStatements extends JdbcDogDao implements DogDao {

    public JdbcDogDaoStatements(JdbcConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    public Dog createDog(Dog dog) throws SQLException {
        try (Connection connection = connectionHolder.getConnection()) {
            final Statement statement = connection.createStatement();
            final int createdRows = statement.executeUpdate(String.format(
                    "INSERT INTO dog (id, name, date_of_birth, height, weight) VALUES ('%s', '%s', '%s', '%d', '%d');",
                    dog.getId(), dog.getName(), dog.getDateOfBirth(), dog.getHeight(), dog.getWeight()
            ));

            if (createdRows == 0) {
                throw new SQLException("Failed to create new dog");
            }
        }
        return dog;
    }

    @Override
    public Dog getDog(UUID id) throws SQLException {
        Dog dog;

        try (Connection connection = connectionHolder.getConnection()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(String.format(
                    "select * from dog where id = '%s'", id
            ));

            dog = mapDog(resultSet);
        }
        return dog;
    }

    @Override
    public Dog updateDog(UUID id, Dog dog) throws SQLException {
        try (Connection connection = connectionHolder.getConnection()) {
            final Statement statement = connection.createStatement();
            final int updatedRows = statement.executeUpdate(String.format(
                    "update dog set NAME = '%s', DATE_OF_BIRTH = '%s', HEIGHT = '%d', WEIGHT = '%d' where id = '%s'",
                    dog.getName(), dog.getDateOfBirth(), dog.getHeight(), dog.getWeight(), id
            ));

            if (updatedRows == 0) {
                throw new SQLException(String.format("Failed to update dog with id %s", dog.getId()));
            }

        }
        return dog;
    }

    @Override
    public void deleteDog(UUID id) throws SQLException {
        try (Connection connection = connectionHolder.getConnection()) {
            final Statement statement = connection.createStatement();
            final int deletedRows = statement.executeUpdate(
                    String.format("delete from dog where id = '%s'", id)
            );

            if (deletedRows == 0) {
                throw new SQLException("Failed to delete dog");
            }
        }
    }
}

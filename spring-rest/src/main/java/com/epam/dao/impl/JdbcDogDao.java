package com.epam.dao.impl;

import com.epam.dao.DogDao;
import com.epam.model.Dog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.java.Log;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.UUID;

@Log
@AllArgsConstructor
public class JdbcDogDao implements DogDao {

    @Getter
    private DataSource dataSource;

    @Override
    public Dog createDog(Dog dog) {
        try (Connection connection = dataSource.getConnection()) {
            final PreparedStatement preparedStatement = connection
                    .prepareStatement(
                            "INSERT INTO dog (name, date_of_birth, height, weight) VALUES (?, ?, ?, ?);",
                            Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, dog.getName());
            preparedStatement.setDate(2, Date.valueOf(dog.getDateOfBirth()));
            preparedStatement.setInt(3, dog.getHeight());
            preparedStatement.setInt(4, dog.getWeight());

            int createdRows = preparedStatement.executeUpdate();

            if (createdRows == 0) {
                throw new SQLException("Failed to create new dog");
            }

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                final String s = resultSet.getObject(1).toString();
                dog.setId(UUID.fromString(s));
            }

        } catch (SQLException e) {
            log.info(e.getMessage());
        }
        return dog;
    }

    @Override
    public Dog getDog(UUID id) {
        Dog dog = null;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from dog where id = ?"
            );
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getObject(1).toString());
                String name = resultSet.getString(2);
                LocalDate dob = resultSet.getDate(3).toLocalDate();
                int height = resultSet.getInt(4);
                int weight = resultSet.getInt(5);

                dog = Dog.builder()
                        .id(uuid)
                        .name(name)
                        .dateOfBirth(dob)
                        .height(height)
                        .weight(weight)
                        .build();
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
        return dog;
    }

    @Override
    public Dog updateDog(UUID id, Dog dog) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update dog set NAME = ?, DATE_OF_BIRTH = ?, HEIGHT = ?, WEIGHT = ? where id =?"
            );
            preparedStatement.setString(1, dog.getName());
            preparedStatement.setDate(2, Date.valueOf(dog.getDateOfBirth()));
            preparedStatement.setInt(3, dog.getHeight());
            preparedStatement.setInt(4, dog.getWeight());
            preparedStatement.setObject(5, id);

            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new SQLException(String.format("Failed to update dog with id %s", dog.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dog;
    }

    @Override
    public void deleteDog(UUID id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from dog where id = ?"
            );
            preparedStatement.setObject(1, id);
            int deletedRows = preparedStatement.executeUpdate();
            if (deletedRows == 0) {
                throw new SQLException("Failed to delete dog");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package com.epam.dao;

import com.epam.model.Dog;

import java.sql.SQLException;
import java.util.UUID;

public interface DogDao {

    Dog createDog(Dog dog) throws SQLException;

    Dog getDog(UUID id) throws SQLException;

    Dog updateDog(UUID id, Dog dog) throws SQLException;

    void deleteDog(UUID id) throws SQLException;
}

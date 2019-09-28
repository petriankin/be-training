package com.epam.dao;

import com.epam.model.Dog;
import lombok.AllArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
public class JdbcDogDao {

    protected JdbcConnectionHolder connectionHolder;

    // TODO: 8/29/2019 move to some util and reduce class hierarchy
    public Dog mapDog(ResultSet resultSet) throws SQLException {
        Dog dog = null;
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
        return dog;
    }
}

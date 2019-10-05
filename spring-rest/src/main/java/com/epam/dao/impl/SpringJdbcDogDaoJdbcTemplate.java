package com.epam.dao.impl;

import com.epam.dao.DogDao;
import com.epam.model.Dog;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@AllArgsConstructor
public class SpringJdbcDogDaoJdbcTemplate implements DogDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Dog createDog(Dog dog) {
        int rows = jdbcTemplate
                .update("INSERT INTO dog (id, name, date_of_birth, height, weight) VALUES (?, ?, ?, ?, ?);",
                        dog.getId(), dog.getName(), dog.getDateOfBirth(), dog.getHeight(), dog.getWeight());
        if (rows == 0) {
            throw new RuntimeException("Failed to create new dog");
        }
        return dog;
    }

    @Override
    public Dog getDog(UUID id) {
        return jdbcTemplate.queryForObject("select * from dog where id = ?", new DogRowMapper(), id);
    }

    @Override
    public Dog updateDog(UUID id, Dog dog) {
        int rows = jdbcTemplate.update("update dog set NAME = ?, DATE_OF_BIRTH = ?, HEIGHT = ?, WEIGHT = ? where id =?",
                dog.getName(), dog.getDateOfBirth(), dog.getHeight(), dog.getWeight(), dog.getId());

        if (rows == 0) {
            throw new RuntimeException(String.format("Failed to update dog with id %s", dog.getId()));
        }
        return dog;
    }

    @Override
    public void deleteDog(UUID id) {
        int rows = jdbcTemplate.update("delete from dog where id = ?", id);
        if (rows == 0) {
            throw new RuntimeException("Failed to delete dog");
        }
    }

    public static class DogRowMapper implements RowMapper<Dog> {

        @Override
        public Dog mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Dog.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .name(rs.getString("name"))
                    .dateOfBirth(rs.getDate("date_of_birth").toLocalDate())
                    .height(rs.getInt("height"))
                    .weight(rs.getInt("weight"))
                    .build();
        }
    }
}
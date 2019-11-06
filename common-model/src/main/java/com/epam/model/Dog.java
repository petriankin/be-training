package com.epam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dog {

    private UUID id;

    @NotNull
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 symbols long")
    private String name;

    @Past(message = "Date of Birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull
    @Positive(message = "Height must be greater than zero")
    private int height;

    @NotNull
    @Positive(message = "Weight must be greater than zero")
    private int weight;

    @OneToMany(mappedBy = "dog")
    private List<House> houses;

    public static Dog mapDog(ResultSet resultSet) throws SQLException {
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

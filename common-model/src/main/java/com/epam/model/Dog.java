package com.epam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
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
}

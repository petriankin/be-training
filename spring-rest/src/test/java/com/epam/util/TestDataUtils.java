package com.epam.util;

import com.epam.model.Dog;
import com.github.javafaker.Faker;

import java.time.LocalDate;

public class TestDataUtils {

    private static Faker faker = new Faker();

    public static Dog generateTestDog() {
        return Dog.builder()
                .name(faker.dog().name())
                .dateOfBirth(LocalDate.now().minusMonths(faker.number().numberBetween(1, 10)))
                .height(faker.number().numberBetween(1, 10))
                .weight(faker.number().numberBetween(1, 20))
                .build();
    }

    public static String generateLongName() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 101; i++) {
            sb.append("a");
        }
        return sb.toString();
    }
}

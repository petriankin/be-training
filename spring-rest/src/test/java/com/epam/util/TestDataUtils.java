package com.epam.util;

import com.epam.model.Dog;

import java.time.LocalDate;

public class TestDataUtils {
    public static Dog generateTestDog() {
        return Dog.builder()
                .name("Name")
                .dateOfBirth(LocalDate.now().minusMonths(1))
                .height(10)
                .weight(10)
                .build();
    }

    public static String generateLongName() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 101; i++) {
            sb.append("a");
        }
        return sb.toString();
    }

    public static Dog copyDog(Dog dog) {
        return Dog.builder()
                .id(dog.getId())
                .name(dog.getName())
                .dateOfBirth(dog.getDateOfBirth())
                .height(dog.getHeight())
                .weight(dog.getWeight())
                .build();
    }
}

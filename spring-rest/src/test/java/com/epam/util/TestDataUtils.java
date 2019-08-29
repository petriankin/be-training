package com.epam.util;

import com.epam.model.Dog;

import java.time.LocalDate;

import static io.qala.datagen.RandomShortApi.integer;
import static io.qala.datagen.RandomShortApi.unicode;

public class TestDataUtils {

    public static Dog generateTestDog() {
        return Dog.builder()
                .name(unicode(1, 100))
                .dateOfBirth(LocalDate.now().minusMonths(integer(1, 10)))
                .height(integer(1, 10))
                .weight(integer(1, 20))
                .build();
    }

    public static String generateLongName() {
        return unicode(101, 102);
    }
}

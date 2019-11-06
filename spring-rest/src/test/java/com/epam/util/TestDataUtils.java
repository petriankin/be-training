package com.epam.util;

import com.epam.model.Dog;
import com.epam.model.House;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public static List<House> generateTestHouses(int amount) {
        List<House> houses = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            houses.add(generateTestHouse());
        }
        return houses;
    }

    public static House generateTestHouse() {
        House house = new House();
        house.setName(unicode(1, 100));
        return house;
    }

    public static String generateLongName() {
        return unicode(101, 102);
    }
}

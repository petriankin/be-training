package com.epam.controller;

import com.epam.model.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.testng.Assert.*;

@Test
@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class DogControllerTest extends AbstractTestNGSpringContextTests {

    private static final String DOG_ID = "931fbc5c-2ffa-4c28-abb9-a89c1c84626e";

    @Autowired
    private DogController dogController;

    @BeforeClass
    public void setUp() {

    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void testCreateDog() {
        Dog dog = generateTestData();

        Dog createdDog = dogController.createDog(dog);

        assertEquals(createdDog.getName(), dog.getName());
        assertEquals(createdDog.getDateOfBirth(), dog.getDateOfBirth());
        assertEquals(createdDog.getHeight(), dog.getHeight());
        assertEquals(createdDog.getWeight(), dog.getWeight());
        assertNotNull(createdDog.getId());
    }

    @Test(dependsOnMethods = {"testCreateDog"})
    public void testGetDog() {
        Dog dog = generateTestData();

        Dog createdDog = dogController.createDog(dog);
        Dog returnedDog = dogController.getDog(createdDog.getId());

        assertNotNull(returnedDog);
        assertEquals(returnedDog, createdDog);
    }

    @Test
    public void testUpdateDog() {
        Dog dog = generateTestData();

        Dog createdDog = dogController.createDog(dog);

        Dog copy = copyDog(createdDog);
        copy.setName("Another name");
        Dog updatedDog = dogController.updateDog(createdDog.getId(), copy);

        assertNotEquals(updatedDog.getName(), copy.getName());

        assertEquals(updatedDog.getId(), copy.getId());
        assertEquals(updatedDog.getDateOfBirth(), copy.getDateOfBirth());
        assertEquals(updatedDog.getHeight(), copy.getHeight());
        assertEquals(updatedDog.getWeight(), copy.getWeight());
    }

    @Test(dependsOnMethods = {"testCreateDog", "testGetDog"})
    public void testDeleteDog() {
        Dog dog = generateTestData();

        Dog createdDog = dogController.createDog(dog);

        dogController.deleteDog(createdDog.getId());

        assertNull(dogController.getDog(createdDog.getId()));


    }

    private static Dog generateTestData() {
        return Dog.builder()
                .name("Name")
                .dateOfBirth(LocalDate.now())
                .height(10)
                .weight(10)
                .build();
    }

    private Dog copyDog(Dog dog) {
        return Dog.builder()
                .id(dog.getId())
                .name(dog.getName())
                .dateOfBirth(dog.getDateOfBirth())
                .height(dog.getHeight())
                .weight(dog.getWeight())
                .build();
    }
}
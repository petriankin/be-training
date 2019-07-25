package com.epam.controller;

import com.epam.model.Dog;
import com.epam.util.TestDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class DogControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DogController dogController;

    @Test
    public void testCreateDog() {
        Dog dog = TestDataUtils.generateTestDog();

        Dog createdDog = dogController.createDog(dog);

        assertEquals(createdDog.getName(), dog.getName());
        assertEquals(createdDog.getDateOfBirth(), dog.getDateOfBirth());
        assertEquals(createdDog.getHeight(), dog.getHeight());
        assertEquals(createdDog.getWeight(), dog.getWeight());
        assertNotNull(createdDog.getId());
    }

    @Test(dependsOnMethods = {"testCreateDog"})
    public void testGetDog() {
        Dog dog = TestDataUtils.generateTestDog();

        Dog createdDog = dogController.createDog(dog);
        Dog returnedDog = dogController.getDog(createdDog.getId());

        assertNotNull(returnedDog);
        assertEquals(returnedDog, createdDog);
    }

    @Test
    public void testUpdateDog() {
        Dog dog = TestDataUtils.generateTestDog();

        Dog createdDog = dogController.createDog(dog);

        Dog copy = TestDataUtils.copyDog(createdDog);
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
        Dog dog = TestDataUtils.generateTestDog();

        Dog createdDog = dogController.createDog(dog);

        dogController.deleteDog(createdDog.getId());

        assertNull(dogController.getDog(createdDog.getId()));


    }


}
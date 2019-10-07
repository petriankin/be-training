package com.epam.service;

import com.epam.controller.DogController;
import com.epam.model.Dog;
import com.epam.util.TestDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Test
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DogControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DogController dogController;

    @Test
    public void testCreateAndGetDog() {
        Dog dog = TestDataUtils.generateTestDog();

        Dog createdDog = dogController.createDog(dog);
        assertThat(createdDog).isEqualToIgnoringGivenFields(dog, "id");
        assertThat(createdDog).extracting("id").isNotNull();

        Dog returnedDog = dogController.getDog(createdDog.getId());
        assertThat(returnedDog).isEqualToComparingFieldByField(createdDog);
    }

    @Test
    public void testUpdateDog() {
        Dog dogToCreate = TestDataUtils.generateTestDog();
        Dog createdDog = dogController.createDog(dogToCreate);

        Dog dogToUpdate = TestDataUtils.generateTestDog();
        Dog updatedDog = dogController.updateDog(createdDog.getId(), dogToUpdate);

        assertThat(createdDog).isNotEqualTo(updatedDog);
        assertThat(updatedDog).isEqualToIgnoringGivenFields(dogToUpdate, "id");
    }

    @Test
//            (dependsOnMethods = {"testCreateAndGetDog"})
    public void testDeleteDog() {
        Dog dog = TestDataUtils.generateTestDog();

        Dog createdDog = dogController.createDog(dog);

        dogController.deleteDog(createdDog.getId());

        assertThat(dogController.getDog(createdDog.getId())).isNull();
    }
}
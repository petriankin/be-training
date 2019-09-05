package com.epam.controller;

import com.epam.model.Dog;
import com.epam.service.TransactionalDogService;
import com.epam.util.TestDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Test
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DogServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TransactionalDogService dogService;

    @Test
    public void testCreateAndGetDog() {
        Dog dog = TestDataUtils.generateTestDog();

        Dog createdDog = dogService.createDog(dog);
        assertThat(createdDog).isEqualToIgnoringGivenFields(dog, "id");
        assertThat(createdDog).extracting("id").isNotNull();

        Dog returnedDog = dogService.getDog(createdDog.getId());
        assertThat(returnedDog).isEqualToComparingFieldByField(createdDog);
    }

    @Test
    public void testUpdateDog() {
        Dog dogToCreate = TestDataUtils.generateTestDog();
        Dog createdDog = dogService.createDog(dogToCreate);

        Dog dogToUpdate = TestDataUtils.generateTestDog();
        Dog updatedDog = dogService.updateDog(createdDog.getId(), dogToUpdate);

        assertThat(createdDog).isNotEqualTo(updatedDog);
        assertThat(updatedDog).isEqualToIgnoringGivenFields(dogToUpdate, "id");
    }

    @Test(dependsOnMethods = {"testCreateAndGetDog"})
    public void testDeleteDog() {
        Dog dog = TestDataUtils.generateTestDog();

        Dog createdDog = dogService.createDog(dog);

        dogService.deleteDog(createdDog.getId());

        assertThat(dogService.getDog(createdDog.getId())).isNull();
    }
}
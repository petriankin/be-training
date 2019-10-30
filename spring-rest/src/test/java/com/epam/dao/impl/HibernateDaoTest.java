package com.epam.dao.impl;

import com.epam.dao.DogDao;
import com.epam.model.Dog;
import com.epam.util.TestDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Test
@ContextConfiguration(locations = {"classpath:contexts/hibernateDao.xml"})
public class HibernateDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DogDao dogDao;
    
    @BeforeMethod
    public void setUp() {
    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void testCreateAndGetDog() {
        Dog dog = TestDataUtils.generateTestDog();

        Dog createdDog = dogDao.createDog(dog);
        assertThat(createdDog).isEqualToIgnoringGivenFields(dog, "id");
        assertThat(createdDog).extracting("id").isNotNull();

        Dog returnedDog = dogDao.getDog(createdDog.getId());
        assertThat(returnedDog).isEqualToComparingFieldByField(createdDog);
    }

    @Test
    public void testUpdateDog() {
        Dog dogToCreate = TestDataUtils.generateTestDog();
        Dog createdDog = dogDao.createDog(dogToCreate);

        Dog dogToUpdate = TestDataUtils.generateTestDog();
        Dog updatedDog = dogDao.updateDog(createdDog.getId(), dogToUpdate);

        assertThat(createdDog).isNotEqualTo(updatedDog);
        assertThat(updatedDog).isEqualToIgnoringGivenFields(dogToUpdate, "id");
    }

    @Test
    public void testDeleteDog() {
        Dog dog = TestDataUtils.generateTestDog();

        Dog createdDog = dogDao.createDog(dog);

        dogDao.deleteDog(createdDog.getId());

        assertThat(dogDao.getDog(createdDog.getId())).isNull();
    }
}
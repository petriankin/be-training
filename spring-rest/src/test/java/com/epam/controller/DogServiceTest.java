package com.epam.controller;

import com.epam.JdbcConnectionHolder;
import com.epam.model.Dog;
import com.epam.service.DogService;
import com.epam.util.TestDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.testng.Assert.*;

@Test
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DogServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DogService dogService;
    @Autowired
    private JdbcConnectionHolder connectionHolder;

    @BeforeClass
    public void setUp() {
        try (Connection connection = connectionHolder.getConnectionWithNoAutoCommit()) {
            Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS " +
                            "dog(id UUID," +
                            " name VARCHAR(100) NOT NULL," +
                            " date_of_birth DATE," +
                            " height INT NOT NULL," +
                            " weight INT NOT NULL);"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() throws SQLException {
        try (Connection connection = connectionHolder.getConnectionWithNoAutoCommit()) {
            Statement statement = connection.createStatement();
            statement.execute(
                    "DROP TABLE IF EXISTS dog;"
            );
        }
    }

    @Test
    public void testCreateAndGetDog() {
        Dog dog = TestDataUtils.generateTestDog();

        Dog createdDog = dogService.createDog(dog);
        assertEquals(createdDog.getName(), dog.getName());
        assertEquals(createdDog.getDateOfBirth(), dog.getDateOfBirth());
        assertEquals(createdDog.getHeight(), dog.getHeight());
        assertEquals(createdDog.getWeight(), dog.getWeight());
        assertNotNull(createdDog.getId());

        Dog returnedDog = dogService.getDog(createdDog.getId());
        assertNotNull(returnedDog);
        assertEquals(returnedDog, createdDog);
    }

    @Test
    // FIXME: 8/8/19
    public void testUpdateDog() {
        Dog dog = TestDataUtils.generateTestDog();
        Dog createdDog = dogService.createDog(dog);

        Dog dogToUpdate = TestDataUtils.generateTestDog();
        dogToUpdate.setId(createdDog.getId());
        Dog updatedDog = dogService.updateDog(createdDog.getId(), dogToUpdate);

        assertEquals(updatedDog.getId(), dogToUpdate.getId());
        assertNotEquals(updatedDog.getName(),
                dogToUpdate.getName());
        assertNotEquals(updatedDog.getDateOfBirth(), dogToUpdate.getDateOfBirth());
        assertNotEquals(updatedDog.getHeight(), dogToUpdate.getHeight());
        assertNotEquals(updatedDog.getWeight(), dogToUpdate.getWeight());
    }

    @Test(dependsOnMethods = {"testCreateAndGetDog"})
    public void testDeleteDog() {
        Dog dog = TestDataUtils.generateTestDog();

        Dog createdDog = dogService.createDog(dog);

        dogService.deleteDog(createdDog.getId());

        assertNull(dogService.getDog(createdDog.getId()));
    }
}
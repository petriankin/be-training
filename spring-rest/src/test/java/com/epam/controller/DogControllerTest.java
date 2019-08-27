package com.epam.controller;

import com.epam.dao.impl.JdbcDogDaoStatements;
import com.epam.model.Dog;
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
public class DogControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DogController dogController;
    @Autowired
    private JdbcDogDaoStatements jdbcDogDao;

    @BeforeClass
    public void setUp() {
        try (Connection connection = jdbcDogDao.getDataSource().getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS " +
                            "dog(id UUID default RANDOM_UUID()," +
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
        try (Connection connection = jdbcDogDao.getDataSource().getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(
                    "DROP TABLE IF EXISTS dog;"
            );
        }
    }


    // TODO: 8/8/19 get and create should be in one test
    @Test
    public void testCreateDog() throws SQLException {
        Dog dog = TestDataUtils.generateTestDog();

        Dog createdDog = dogController.createDog(dog);

        assertEquals(createdDog.getName(), dog.getName());
        assertEquals(createdDog.getDateOfBirth(), dog.getDateOfBirth());
        assertEquals(createdDog.getHeight(), dog.getHeight());
        assertEquals(createdDog.getWeight(), dog.getWeight());
        assertNotNull(createdDog.getId());
    }

    @Test(dependsOnMethods = {"testCreateDog"})
    public void testGetDog() throws SQLException {
        Dog dog = TestDataUtils.generateTestDog();

        Dog createdDog = dogController.createDog(dog);
        Dog returnedDog = dogController.getDog(createdDog.getId());

        assertNotNull(returnedDog);
        assertEquals(returnedDog, createdDog);
    }

    @Test
    // FIXME: 8/8/19
    public void testUpdateDog() throws SQLException {
        Dog dog = TestDataUtils.generateTestDog();
        Dog createdDog = dogController.createDog(dog);

        Dog dogToUpdate = TestDataUtils.generateTestDog();
        dogToUpdate.setId(createdDog.getId());
        Dog updatedDog = dogController.updateDog(createdDog.getId(), dogToUpdate);

        assertEquals(updatedDog.getId(), dogToUpdate.getId());
        assertNotEquals(updatedDog.getName(),
                dogToUpdate.getName());
        assertNotEquals(updatedDog.getDateOfBirth(), dogToUpdate.getDateOfBirth());
        assertNotEquals(updatedDog.getHeight(), dogToUpdate.getHeight());
        assertNotEquals(updatedDog.getWeight(), dogToUpdate.getWeight());
    }

    @Test(dependsOnMethods = {"testCreateDog", "testGetDog"})
    public void testDeleteDog() throws SQLException {
        Dog dog = TestDataUtils.generateTestDog();

        Dog createdDog = dogController.createDog(dog);

        dogController.deleteDog(createdDog.getId());

        assertNull(dogController.getDog(createdDog.getId()));
    }
}
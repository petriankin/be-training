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

import static org.assertj.core.api.Assertions.assertThat;

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
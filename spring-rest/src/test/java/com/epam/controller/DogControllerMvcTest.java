//package com.epam.controller;
//
//import com.epam.dao.impl.JdbcDogDaoStatements;
//import com.epam.model.Dog;
//import com.epam.util.TestDataUtils;
//import io.restassured.http.ContentType;
//import io.restassured.module.mockmvc.RestAssuredMockMvc;
//import io.restassured.module.mockmvc.response.MockMvcResponse;
//import org.hamcrest.Matchers;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mock.web.MockServletContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import javax.servlet.ServletContext;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
//import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
//import static org.hamcrest.Matchers.notNullValue;
//import static org.testng.Assert.*;
//import static org.testng.AssertJUnit.assertEquals;
//import static org.testng.AssertJUnit.assertNotNull;
//
//@Test
//@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
//@WebAppConfiguration
//public class DogControllerMvcTest extends AbstractTestNGSpringContextTests {
//
//    private static final String URL = "/dog";
//
//    @Autowired
//    private DogController dogController;
//    @Autowired
//    private JdbcDogDaoStatements jdbcDogDao;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    private MockMvc mockMvc;
//
//    @BeforeClass
//    public void initialiseRestAssuredMockMvcWebApplicationContext() {
//        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
//    }
//
//
//    @Test
//    public void givenContext_whenServletContext_thenItProvidesGreetController() {
//        ServletContext servletContext = webApplicationContext.getServletContext();
//
//        assertNotNull(servletContext);
//        assertTrue(servletContext instanceof MockServletContext);
//        assertNotNull(webApplicationContext.getBean("dogController"));
//    }
//
//
//    @BeforeClass
//    public void setUp() {
//        try (Connection connection = jdbcDogDao.getDataSource().getConnection()) {
//            Statement statement = connection.createStatement();
//            statement.execute(
//                    "CREATE TABLE IF NOT EXISTS " +
//                            "dog(id UUID default RANDOM_UUID()," +
//                            " name VARCHAR(100) NOT NULL," +
//                            " date_of_birth DATE," +
//                            " height INT NOT NULL," +
//                            " weight INT NOT NULL);"
//            );
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @AfterClass
//    public void tearDown() throws SQLException {
//        try (Connection connection = jdbcDogDao.getDataSource().getConnection()) {
//            Statement statement = connection.createStatement();
//            statement.execute(
//                    "DROP TABLE IF EXISTS dog;"
//            );
//        }
//    }
//
//
//    // TODO: 8/8/19 get and create should be in one test
//    @Test
//    public void testCreateDog() throws SQLException {
//        Dog dog = TestDataUtils.generateTestDog();
//
//        final MockMvcResponse response = given()
//                .body(dog)
//                .contentType(ContentType.JSON)
//                .when()
//                .post(URL);
//
//        response.then()
//                .statusCode(200)
//                .body(notNullValue());
//
//        Dog returnedDog = response.as(Dog.class);
//
//        assertNotNull(returnedDog.getId());
//
//        Dog fromDb = given()
//                .when()
//                .get(URL + "/" + returnedDog.getId())
//                .as(Dog.class);
//
//        assertEquals(fromDb, returnedDog);
//    }
//
//    @Test(dependsOnMethods = {"testCreateDog"})
//    public void testGetDog() throws SQLException {
//        Dog dog = TestDataUtils.generateTestDog();
//
//        Dog createdDog = dogController.createDog(dog);
//        Dog returnedDog = dogController.getDog(createdDog.getId());
//
//        assertNotNull(returnedDog);
//        assertEquals(returnedDog, createdDog);
//    }
//
//    @Test
//    // FIXME: 8/8/19
//    public void testUpdateDog() throws SQLException {
//        Dog dog = TestDataUtils.generateTestDog();
//        Dog createdDog = dogController.createDog(dog);
//
//        Dog dogToUpdate = TestDataUtils.generateTestDog();
//        dogToUpdate.setId(createdDog.getId());
//        Dog updatedDog = dogController.updateDog(createdDog.getId(), dogToUpdate);
//
//        assertEquals(updatedDog.getId(), dogToUpdate.getId());
//        assertNotEquals(updatedDog.getName(),
//                dogToUpdate.getName());
//        assertNotEquals(updatedDog.getDateOfBirth(), dogToUpdate.getDateOfBirth());
//        assertNotEquals(updatedDog.getHeight(), dogToUpdate.getHeight());
//        assertNotEquals(updatedDog.getWeight(), dogToUpdate.getWeight());
//    }
//
//    @Test(dependsOnMethods = {"testCreateDog", "testGetDog"})
//    public void testDeleteDog() throws SQLException {
//        Dog dog = TestDataUtils.generateTestDog();
//
//        Dog createdDog = dogController.createDog(dog);
//
//        dogController.deleteDog(createdDog.getId());
//
//        assertNull(dogController.getDog(createdDog.getId()));
//    }
//
//
//    /////////////////////////////
//
////    @AfterClass
////    public void resetContext() {
////        RestAssuredMockMvc.reset();
////    }
////
////    @Test
////    public void whenTryToGetNotExistedDog_notFoundStatusCode() {
////        getDogById(qnegativeLong()).then().statusCode(404);
////    }
////
////    @Test
////    public void whenTryToAddInvalidDog_exception() {
////        Dog toSave = new Dog().setHeight(integer(1, 100)).setWeight(integer(1, 100)).setName(english(101));
////        saveDog(toSave).then().statusCode(400);
////    }
////
////    @Test
////    public void getByIdReturnsCorrectDog() {
////        long existingId = 1L;
////        Dog fromDb = getDogById(existingId).as(Dog.class);
////        assertEquals(fromDb.getId().longValue(), existingId);
////        assertEquals(fromDb.getName(), "ABBA");
////    }
////
////    @Test
////    public void whenAddNewDog_itSaved() {
////        Dog toSave = new Dog().setHeight(integer(1, 100)).setWeight(integer(1, 100)).setName(english(1, 100));
////
////
////    }
////
////    @Test
////    public void whenRemoveDog_itIsRemoved() {
////        Dog toSave = new Dog().setHeight(integer(1, 100)).setWeight(integer(1, 100)).setName(english(1, 100));
////        Dog fromResponse = saveDog(toSave).as(Dog.class);
////
////        MockMvcResponse deleteResponse = removeDogById(fromResponse.getId());
////        deleteResponse.then().statusCode(204);
////
////        getDogById(fromResponse.getId()).then().body(Matchers.isEmptyOrNullString());
////    }
////
////    private MockMvcResponse getDogById(long id) {
////        return given().when().get(API_PATH + "/" + id);
////    }
////
////    private MockMvcResponse saveDog(Dog toSave) {
////        return given().body(toSave).contentType(ContentType.JSON).when().post(API_PATH);
////    }
////
////    private MockMvcResponse removeDogById(Long id) {
////        return given().contentType(ContentType.JSON).when().delete(API_PATH + "/" + id);
////    }
//}
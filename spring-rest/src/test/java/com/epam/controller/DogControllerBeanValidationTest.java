package com.epam.controller;

import com.epam.model.Dog;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class DogControllerBeanValidationTest {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator va1lidator = factory.getValidator();

    @Test
    public void testWhenNameIsLessThan1SymbolLong_validationFailed() {
        Dog dog = Dog.builder()
                .dateOfBirth(LocalDate.now().minusMonths(1))
                .name("")
                .height(10)
                .weight(10)
                .build();

        Set<ConstraintViolation<Dog>> violations = va1lidator.validate(dog);
        String message = violations.stream()
                .findAny()
                .get()
                .getMessage();

        assertFalse(violations.isEmpty());
        assertEquals(message, "Name must be between 1 and 100 symbols long");
    }

    @Test
    public void testWhenNameIsMoreThan100SymbolsLong_validationFailed() {
        Dog dog = Dog.builder()
                .dateOfBirth(LocalDate.now().minusMonths(1))
                .name(generateLongName())
                .height(10)
                .weight(10)
                .build();

        Set<ConstraintViolation<Dog>> violations = va1lidator.validate(dog);
        String message = violations.stream()
                .findAny()
                .get()
                .getMessage();

        assertFalse(violations.isEmpty());
        assertEquals(message, "Name must be between 1 and 100 symbols long");
    }

    @Test
    public void testWhenDateIsNotInThePast_validationFailed() {
        Dog dog = Dog.builder()
                .dateOfBirth(LocalDate.now())
                .name("Name")
                .height(10)
                .weight(10)
                .build();

        Set<ConstraintViolation<Dog>> violations = va1lidator.validate(dog);
        String message = violations.stream()
                .findAny()
                .get()
                .getMessage();

        assertFalse(violations.isEmpty());
        assertEquals(message, "Date of Birth must be in the past");
    }

    @Test
    public void testWhenHeightIsNotPositive_validationFailed() {
        Dog dog = Dog.builder()
                .dateOfBirth(LocalDate.now().minusMonths(1))
                .name("Name")
                .height(0)
                .weight(10)
                .build();

        Set<ConstraintViolation<Dog>> violations = va1lidator.validate(dog);
        String message = violations.stream()
                .findAny()
                .get()
                .getMessage();

        assertFalse(violations.isEmpty());
        assertEquals(message, "Height must be greater than zero");
    }

    @Test
    public void testWhenWeightIsNotPositive_validationFailed() {
        Dog dog = Dog.builder()
                .dateOfBirth(LocalDate.now().minusMonths(1))
                .name("Name")
                .height(10)
                .weight(0)
                .build();

        Set<ConstraintViolation<Dog>> violations = va1lidator.validate(dog);
        String message = violations.stream()
                .findAny()
                .get()
                .getMessage();

        assertFalse(violations.isEmpty());
        assertEquals(message, "Weight must be greater than zero");
    }

    private String generateLongName() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 101; i++) {
            sb.append("a");
        }
        return sb.toString();
    }


}
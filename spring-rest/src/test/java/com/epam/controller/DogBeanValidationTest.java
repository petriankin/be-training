package com.epam.controller;

import com.epam.model.Dog;
import com.epam.util.TestDataUtils;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DogBeanValidationTest {

    private Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    @Test
    public void whenAllFieldsAreValid_validationPassed() {
        Dog dog = TestDataUtils.generateTestDog();

        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);

        assertThat(violations).isEmpty();
    }

    @Test
    public void whenNameIsLessThan1SymbolLong_validationFailed() {
        Dog dog = TestDataUtils.generateTestDog();
        dog.setName("");

        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        String message = violations.stream()
                .findAny()
                .get()
                .getMessage();

        assertThat(violations).isNotEmpty();
        assertThat(message).isEqualToIgnoringCase("Name must be between 1 and 100 symbols long");
    }

    @Test
    public void whenNameIsMoreThan100SymbolsLong_validationFailed() {
        Dog dog = TestDataUtils.generateTestDog();
        dog.setName(TestDataUtils.generateLongName());

        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        String message = violations.stream()
                .findAny()
                .get()
                .getMessage();

        assertThat(violations).isNotEmpty();
        assertThat(message).isEqualToIgnoringCase("Name must be between 1 and 100 symbols long");
    }

    @Test
    public void whenDateIsNotInThePast_validationFailed() {
        Dog dog = TestDataUtils.generateTestDog();
        dog.setDateOfBirth(LocalDate.now());

        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        String message = violations.stream()
                .findAny()
                .get()
                .getMessage();

        assertThat(violations).isNotEmpty();
        assertThat(message).isEqualToIgnoringCase("Date of Birth must be in the past");
    }

    @Test
    public void whenHeightIsNotPositive_validationFailed() {
        Dog dog = TestDataUtils.generateTestDog();
        dog.setHeight(0);

        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        String message = violations.stream()
                .findAny()
                .get()
                .getMessage();

        assertThat(violations).isNotEmpty();
        assertThat(message).isEqualToIgnoringCase("Height must be greater than zero");
    }

    @Test
    public void whenWeightIsNotPositive_validationFailed() {
        Dog dog = TestDataUtils.generateTestDog();
        dog.setWeight(0);

        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        String message = violations.stream()
                .findAny()
                .get()
                .getMessage();

        assertThat(violations).isNotEmpty();
        assertThat(message).isEqualToIgnoringCase("Weight must be greater than zero");
    }
}
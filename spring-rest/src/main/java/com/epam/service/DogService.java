package com.epam.service;

import com.epam.model.Dog;

import java.util.UUID;

public interface DogService {
    Dog createDog(Dog dog);

    Dog getDog(UUID id);

    Dog updateDog(UUID id, Dog dog);

    void deleteDog(UUID id);
}

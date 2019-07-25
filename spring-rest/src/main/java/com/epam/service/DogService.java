//package com.epam.service;
//
//import com.epam.model.Dog;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//@Service
//@Deprecated
//// FIXME: 7/22/19 not used for now
//public class DogService {
//
//    private static Map<UUID, Dog> dogs = new HashMap<>();
//
//    public DogService() {
//            for (int i = 0; i < 5; i++) {
//                UUID id = UUID.randomUUID();
//                System.out.println(id);
//                Dog dog = new Dog();
//                dog.setId(id);
//                dog.setName("Dog" + i);
//                dog.setDateOfBirth(LocalDate.now());
//                dog.setHeight(20 + i);
//                dog.setWeight(5 + i);
//
//                dogs.put(id, dog);
//            }
//    }
//
//    public Dog createDog(Dog dog) {
//        return dogs.put(UUID.randomUUID(), dog);
//    }
//
//    public Dog getDog(UUID id) {
//        return dogs.getOrDefault(id, new Dog());
//    }
//
//    public Dog updateDog(UUID id, Dog dog) {
//        return dogs.put(id, dog);
//    }
//
//    public void deleteDog(UUID id) {
//        dogs.remove(id);
//    }
//
//}

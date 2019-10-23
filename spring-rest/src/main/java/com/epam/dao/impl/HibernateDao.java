package com.epam.dao.impl;

import com.epam.dao.DogDao;
import com.epam.model.Dog;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@AllArgsConstructor
public class HibernateDao implements DogDao {

    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public Dog createDog(Dog dog) {
        getCurrentSession().save(dog);
        return dog;
    }

    @Override
    @Transactional(readOnly = true)
    public Dog getDog(UUID id) {
        return getCurrentSession().get(Dog.class, id);
    }

    @Override
    @Transactional
    public Dog updateDog(UUID id, Dog dog) {
        dog.setId(id);
        getCurrentSession().saveOrUpdate(dog);
        return dog;
    }

    @Override
    @Transactional
    public void deleteDog(UUID id) {
        Dog dog = getCurrentSession().get(Dog.class, id);
        getCurrentSession().delete(dog);
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}

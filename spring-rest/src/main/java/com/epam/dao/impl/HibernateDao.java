package com.epam.dao.impl;

import com.epam.dao.DogDao;
import com.epam.model.Dog;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.UUID;

@AllArgsConstructor
public class HibernateDao implements DogDao {

    private SessionFactory sessionFactory;

    @Override
    public Dog createDog(Dog dog) {
        getCurrentSession().save(dog);
        return dog;
    }

    @Override
    public Dog getDog(UUID id) {
        return getCurrentSession().get(Dog.class, id);
    }

    @Override
    public Dog updateDog(UUID id, Dog dog) {
        getCurrentSession().saveOrUpdate(dog);
        return dog;
    }

    @Override
    public void deleteDog(UUID id) {
        Dog dog = getCurrentSession().get(Dog.class, id);
        getCurrentSession().delete(dog);
    }

    // FIXME: 10/23/2019
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}

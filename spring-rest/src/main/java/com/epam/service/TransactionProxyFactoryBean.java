package com.epam.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

@AllArgsConstructor
public class TransactionProxyFactoryBean implements FactoryBean<DogService> {

    private TransactionalProxy transactionalProxy;

    @Override
    public DogService getObject() {
        Class<DogServiceImpl> clazz = DogServiceImpl.class;
            return (DogService) Proxy.newProxyInstance(this.getClass().getClassLoader(), clazz.getInterfaces(),
                transactionalProxy);
    }

    @Override
    public Class<?> getObjectType() {
        return DogService.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}

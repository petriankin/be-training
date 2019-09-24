package com.epam.service;

import lombok.AllArgsConstructor;
import net.sf.cglib.proxy.Enhancer;
import org.springframework.beans.factory.FactoryBean;

@AllArgsConstructor
public class TransactionalInterceptorFactoryBean implements FactoryBean<DogService> {

    private TransactionalInterceptor transactionalInterceptor;

    @Override
    public DogService getObject() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DogServiceImpl.class);
        enhancer.setCallback(transactionalInterceptor);
        return (DogService) enhancer.create();
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


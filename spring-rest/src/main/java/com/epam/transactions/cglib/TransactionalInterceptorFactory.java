package com.epam.transactions.cglib;

import com.epam.service.DogService;
import com.epam.service.impl.DogServiceImpl;
import lombok.AllArgsConstructor;
import net.sf.cglib.proxy.Enhancer;

@AllArgsConstructor
public class TransactionalInterceptorFactory {

    private TransactionalInterceptor transactionalInterceptor;

    public DogService getObject() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DogServiceImpl.class);
        enhancer.setCallback(transactionalInterceptor);
        return (DogService) enhancer.create();
    }
}


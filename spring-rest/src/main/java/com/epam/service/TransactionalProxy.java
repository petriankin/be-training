package com.epam.service;

import com.epam.JdbcConnectionHolder;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@AllArgsConstructor
public class TransactionalProxy implements InvocationHandler {

    private DogService dogService;
    private JdbcConnectionHolder connectionHolder;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        try {
            connectionHolder.startTransaction();
            result = method.invoke(dogService, args);
            connectionHolder.commitTransaction();
        } catch (Exception e) {
            connectionHolder.rollbackTransaction();
            throw new RuntimeException(e);
        } finally {
            connectionHolder.closeConnection();
        }
        return result;
    }
}

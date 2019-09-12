package com.epam.service;

import com.epam.JdbcConnectionHolder;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.datasource.ConnectionHolder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@AllArgsConstructor
public class TransactionalProxy implements InvocationHandler {

    private Object object;
    JdbcConnectionHolder connectionHolder;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        return null;
    }
}

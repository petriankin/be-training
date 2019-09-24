package com.epam.service;

import com.epam.JdbcConnectionHolder;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TransactionalInterceptor implements MethodInterceptor {

    private JdbcConnectionHolder connectionHolder;

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result;
        try {
            connectionHolder.startTransaction();
            result = methodProxy.invokeSuper(o, args);
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
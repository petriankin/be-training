package com.epam.transactions.cglib;

import com.epam.dao.JdbcConnectionHolder;
import lombok.AllArgsConstructor;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@AllArgsConstructor
public class TransactionalInterceptor implements MethodInterceptor {

    private JdbcConnectionHolder connectionHolder;
    private Object proxiedObject;

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result;
        try {
            connectionHolder.startTransaction();
            result = method.invoke(proxiedObject, args);
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
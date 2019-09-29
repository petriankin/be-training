package com.epam.aop;


import com.epam.dao.JdbcConnectionHolder;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;

@AllArgsConstructor
public class TransactionalAspect {

    private JdbcConnectionHolder connectionHolder;

    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            connectionHolder.startTransaction();
            result = joinPoint.proceed();
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
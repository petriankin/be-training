package com.epam.service;

import com.epam.JdbcConnectionHolder;
import com.epam.dao.impl.JdbcDogDaoPreparedStatements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Proxy;

import static org.testng.Assert.*;

@Test
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TransactionalProxyTest extends AbstractTestNGSpringContextTests {

//    @Qualifier("dogServiceImpl")
//    @Autowired
//    private DogServiceImpl dogService;
//
//    @Autowired
//    private JdbcConnectionHolder jdbcConnectionHolder;

    @Autowired
    private TransactionalProxy transactionalProxy;

    @BeforeMethod
    public void setUp() {
    }

    @Test
    public void testInvoke() {
        Class<DogServiceImpl> clazz = DogServiceImpl.class;
        DogService dogService = (DogService) Proxy.newProxyInstance(clazz.getClassLoader(),
                clazz.getInterfaces(),
                transactionalProxy);
        
    }
}
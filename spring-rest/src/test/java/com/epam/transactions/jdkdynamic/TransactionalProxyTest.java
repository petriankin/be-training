package com.epam.transactions.jdkdynamic;

import com.epam.service.DogService;
import com.epam.service.impl.DogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

@Test
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TransactionalProxyTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TransactionalProxy transactionalProxy;

    @Test
    public void testInvoke() {
        Class<DogServiceImpl> clazz = DogServiceImpl.class;
        DogService dogService = (DogService) Proxy.newProxyInstance(clazz.getClassLoader(),
                clazz.getInterfaces(),
                transactionalProxy);
        assertThat(dogService).isNotNull();
    }
}
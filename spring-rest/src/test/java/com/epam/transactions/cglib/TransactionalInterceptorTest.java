package com.epam.transactions.cglib;

import com.epam.service.DogService;
import com.epam.service.impl.DogServiceImpl;
import net.sf.cglib.proxy.Enhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Test
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TransactionalInterceptorTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TransactionalInterceptor transactionalInterceptor;

    @Test
    public void testIntercept() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DogServiceImpl.class);
        enhancer.setCallback(transactionalInterceptor);
        DogService dogService = (DogService) enhancer.create();
        assertThat(dogService).isNotNull();
    }
}
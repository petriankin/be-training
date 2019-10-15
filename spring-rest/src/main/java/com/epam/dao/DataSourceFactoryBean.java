package com.epam.dao;

import org.springframework.beans.factory.FactoryBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DataSourceFactoryBean implements FactoryBean<DataSource> {
    @Override
    public DataSource getObject() throws Exception {
        Context context = new InitialContext();
        return (DataSource) context.lookup("java:/comp/env/jdbc/MyLocalDB");
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}

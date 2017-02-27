package org.oopdev.jio.dsql.api.test.tools;

/**
 * Created by kamilbukum on 20/11/16.
 */
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class HibernateUtilTest {

    private static Map<String, SessionFactory> factoryMap = new LinkedHashMap<>();
    public static SessionFactory buildSessionFactory(Class<?> testClass, String scanPackage){
        Configuration configuration = new Configuration();
        configuration.setProperty("connection.driver_class","org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:" + testClass.getSimpleName());
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperty("show_sql", "true");
        configuration.setProperty(" hibernate.connection.pool_size", "10");

        Reflections reflections = new Reflections(scanPackage);

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(javax.persistence.Entity.class);

        for(Class<?> clazz : classes) {
            configuration.addAnnotatedClass(clazz);
        }

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
    public static SessionFactory getSessionFactory(Class<?> testClass, String scanPackage) {
        SessionFactory sessionFactory = factoryMap.computeIfAbsent(testClass.getSimpleName(), k -> buildSessionFactory(testClass, scanPackage));
        return sessionFactory;
    }
}
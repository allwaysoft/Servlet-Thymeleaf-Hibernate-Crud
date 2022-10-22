package com.example.crudweb.util;

import com.example.crudweb.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory != null) {
            return sessionFactory;
        } else {
            try {
                Configuration configuration = new Configuration().configure();
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                configuration.addAnnotatedClass(User.class);
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Throwable ex) {
                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }
            return sessionFactory;
        }
    }
}

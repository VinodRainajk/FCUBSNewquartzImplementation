package Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateConfiguration {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                                                      .configure() // configures settings from hibernate.cfg.xml
                                                      .build();
    private SessionFactory factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public Session getHibernateSession()
    {
         return factory.openSession();
    }

}

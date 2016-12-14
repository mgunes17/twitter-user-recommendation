package configuration;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by ercan on 14.12.2016.
 */
public class HibernateConfiguration {
    private static SessionFactory sessionFactory = null;

    public HibernateConfiguration(){

    }

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration c = new Configuration();
            c = c.configure();

            return c.buildSessionFactory();
        }
        catch(Throwable ex) {
            System.err.println("yaratılamadı:" + ex.getMessage());
            ex.getCause().printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null)
            sessionFactory = buildSessionFactory();

        return sessionFactory;
    }
}

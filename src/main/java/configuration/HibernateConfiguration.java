package configuration;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ercan on 14.12.2016.
 */
public class HibernateConfiguration {
    private static SessionFactory sessionFactory = null;

    public HibernateConfiguration(){

    }

    private static SessionFactory buildSessionFactory() {
        try {
            Properties prop = new Properties();
            String propFileName = "hibernate.properties";

            InputStream inputStream = new HibernateConfiguration().getClass().getClassLoader().getResourceAsStream(propFileName);
            prop.load(inputStream);

            Configuration c = new Configuration();
            c = c.configure();
            c.setProperty("hibernate.connection.username", prop.getProperty("db_username"));
            c.setProperty("hibernate.connection.password", prop.getProperty("db_password"));
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

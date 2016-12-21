package db.hibernate;

import configuration.HibernateConfiguration;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by mgunes on 17.12.2016.
 */

public class AbstractDAO {
    private Session session;

    protected <T> boolean saveList(List<T> list) {
        session = HibernateConfiguration.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            for(Object pl: list){
                session.saveOrUpdate(pl);
            }

            session.flush();
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());;
            ex.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    protected <T> List<T> getAllRows(String className) {
        session = HibernateConfiguration.getSessionFactory().openSession();

        try {
            String select = "FROM " + className;
            Query query = session.createQuery(select);
            List<Object> rows = query.list();
            return (List<T>) rows;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());;
            ex.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}

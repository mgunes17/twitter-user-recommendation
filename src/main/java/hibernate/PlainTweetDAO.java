package hibernate;

import configuration.HibernateConfiguration;
import model.PlainTweet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.postgresql.util.PSQLException;

import java.util.List;

/**
 * Created by ercan on 14.12.2016.
 */
public class PlainTweetDAO {
    private Session session;

    public boolean savePlainTweetList(List<PlainTweet> plainTweets){
        session = HibernateConfiguration.getSessionFactory().openSession();
        try{
            session.beginTransaction();

            for(PlainTweet pl: plainTweets){
                session.saveOrUpdate(pl);
            }

            session.flush();
            session.getTransaction().commit();

            return true;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }finally {
            session.close();
        }
    }
}

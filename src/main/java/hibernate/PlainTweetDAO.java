package hibernate;

import configuration.HibernateConfiguration;
import model.PlainTweet;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ercan on 14.12.2016.
 */
public class PlainTweetDAO extends AbstractDAO {
    private Session session;

    public boolean savePlainTweetList(List<PlainTweet> plainTweets){
        return saveList(plainTweets);
    }

    public List<PlainTweet> getRawTweets() {
        List<PlainTweet> plainTweets = new ArrayList<PlainTweet>();
        session = HibernateConfiguration.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            String sql = "select p.id, p.tweet " +
                    " from ordered_word_list o, plain_tweet p " +
                    " where o.id <> p.id  and p.tweet NOT LIKE 'RT%'";

            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(PlainTweet.class);

        } catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            session.getTransaction().rollback();
            return null;
        } finally {
        session.close();
        }

        return plainTweets;
    }
}

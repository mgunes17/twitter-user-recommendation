package hibernate;

import configuration.HibernateConfiguration;
import model.ParsedTweet;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by mgunes on 17.12.2016.
 */

public class ParsedTweetDAO extends AbstractDAO {
    private Session session;

    public boolean saveParsedList(List<ParsedTweet> parsedTweets) {
        return saveList(parsedTweets);
    }

    public List<ParsedTweet> getRandomParsedTweet(int count) {
        session = HibernateConfiguration.getSessionFactory().openSession();

        try {
            String select = "SELECT * FROM ordered_word_list WHERE category IS NULL ORDER BY RANDOM() LIMIT " + count;
            SQLQuery query = session.createSQLQuery(select);
            query.addEntity(ParsedTweet.class);
            List<ParsedTweet> parsedTweetList = query.list();
            return parsedTweetList;
        } catch (Exception ex) {
            System.out.println("getRandomParsedTweet " + ex.getMessage());
            ex.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}

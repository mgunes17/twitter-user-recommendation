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
        String select = "SELECT * FROM ordered_word_list WHERE category IS NULL ORDER BY RANDOM() LIMIT " + count;
        return getList(select);
    }

    public List<ParsedTweet> getLabeledTweet() {
        String select = "SELECT * FROM ordered_word_list WHERE category IS NOT NULL AND category <> 7";
       return getList(select);
    }

    private List<ParsedTweet> getList(String select) {
        session = HibernateConfiguration.getSessionFactory().openSession();

        try {
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

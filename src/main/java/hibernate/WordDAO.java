package hibernate;

import configuration.HibernateConfiguration;
import model.AllWords;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by mgunes on 17.12.2016.
 */
public class WordDAO {
    private Session session;

    public boolean isWordExist(String word) {
        session = HibernateConfiguration.getSessionFactory().openSession();

        try {
            String hql = "FROM AllWords WHERE word = :word";
            Query query = session.createQuery(hql);
            query.setString("word", word);

            List<AllWords> wordList = query.list();

            if(wordList.size() == 0)
                return false;
            else
                return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
}

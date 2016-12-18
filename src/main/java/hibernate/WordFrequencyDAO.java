package hibernate;

import configuration.HibernateConfiguration;
import model.WordFrequency;
import org.hibernate.Session;
import training.TrainingData;

import java.util.Map;

/**
 * Created by mgunes on 19.12.2016.
 */
public class WordFrequencyDAO extends AbstractDAO {
    private Session session;

    public boolean saveWordMap(Map<Integer, TrainingData> trainingDataMap) {
        session = HibernateConfiguration.getSessionFactory().openSession();
        session.beginTransaction();

        try {
            for(Integer key: trainingDataMap.keySet()) {
                for(String s: trainingDataMap.get(key).getWordFrequency().keySet()) {
                    WordFrequency wordFrequency = new WordFrequency();
                    wordFrequency.setCount(trainingDataMap.get(key).getWordFrequency().get(s));
                    wordFrequency.setWord(s);
                    wordFrequency.setCategory(trainingDataMap.get(key).getCategory());

                    session.save(wordFrequency);
                }
            }

            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            System.out.println("saveWordMap: " + ex.getMessage());
            return false;
        } finally {
            session.close();
        }
    }
}

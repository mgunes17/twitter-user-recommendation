package db.hibernate;

import configuration.HibernateConfiguration;
import db.compositekey.WordFrequencyPK;
import db.model.Category;
import db.model.WordFrequency;
import org.hibernate.Session;
import training.TrainingData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mgunes on 19.12.2016.
 */
public class WordFrequencyDAO extends AbstractDAO {
    private Session session;

    public List<WordFrequency> getWordFrequencyList(){
        return getAllRows("WordFrequency");
    }

    public boolean saveWordMap(Map<Integer, TrainingData> trainingDataMap) {
        try {
            session = HibernateConfiguration.getSessionFactory().openSession();
            session.beginTransaction();
            int i = 0;
            for(Integer key: trainingDataMap.keySet()) {
                for(String s: trainingDataMap.get(key).getWordFrequency().keySet()) {
                    WordFrequency wordFrequency = new WordFrequency();
                    wordFrequency.setId(++i);
                    wordFrequency.setCount(trainingDataMap.get(key).getWordFrequency().get(s));
                    WordFrequencyPK wordFrequencyPK = new WordFrequencyPK();
                    wordFrequencyPK.setCategory(trainingDataMap.get(key).getCategory());
                    wordFrequencyPK.setWord(s);
                    wordFrequency.setWordFrequencyPK(wordFrequencyPK);

                    session.saveOrUpdate(wordFrequency);
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


    public Map<Integer, TrainingData> setupTrainingData(){
        try {
            session = HibernateConfiguration.getSessionFactory().openSession();
            session.beginTransaction();
            CategoryDAO categoryDAO = new CategoryDAO();
            Map<Integer, Category> categoryMap = categoryDAO.getCategoryAsMap();
            Map<Integer, TrainingData> trainingDataMap = new HashMap<Integer, TrainingData>();

            for(Integer key : categoryMap.keySet()){
                trainingDataMap.put(key, new TrainingData());
            }

            List<WordFrequency> wordFrequencies = getWordFrequencyList();
            int categoryId;
            String word;
            int wordCount;
            for(WordFrequency wf: wordFrequencies){
                word = wf.getWordFrequencyPK().getWord();
                categoryId = wf.getWordFrequencyPK().getCategory().getId();
                wordCount = wf.getCount();
                trainingDataMap.get(categoryId).setCategory(categoryMap.get(categoryId));
                trainingDataMap.get(categoryId).getWordFrequency().put(word, wordCount);
            }

            session.getTransaction().commit();
            return trainingDataMap;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            System.out.println("setupTrainingData: " + ex.getMessage());
            return new HashMap<Integer, TrainingData>();
        } finally {
            session.close();
        }
    }
}

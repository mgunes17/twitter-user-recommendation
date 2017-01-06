package db.hibernate;

import configuration.HibernateConfiguration;
import db.compositekey.WordCategoryFrequencyPK;
import db.model.Category;
import db.model.WordCategoryFrequency;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import training.CategoryTrainingData;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCategoryFrequencyDAO extends AbstractDAO {
    private Session session;

    public List<WordCategoryFrequency> getWordFrequencyList(){
        return getAllRows("WordCategoryFrequency");
    }

    public boolean saveWordMap(Map<Integer, CategoryTrainingData> trainingDataMap) {
        try {
            session = HibernateConfiguration.getSessionFactory().openSession();
            session.beginTransaction();
            int i = 0;
            for(Integer key: trainingDataMap.keySet()) {
                for(String s: trainingDataMap.get(key).getWordFrequency().keySet()) {
                    WordCategoryFrequency wordCategoryFrequency = new WordCategoryFrequency();
                    wordCategoryFrequency.setId(++i);
                    wordCategoryFrequency.setCount(trainingDataMap.get(key).getWordFrequency().get(s));
                    WordCategoryFrequencyPK wordCategoryFrequencyPK = new WordCategoryFrequencyPK();
                    wordCategoryFrequencyPK.setCategory(trainingDataMap.get(key).getCategory());
                    wordCategoryFrequencyPK.setWord(s);
                    wordCategoryFrequency.setWordCategoryFrequencyPK(wordCategoryFrequencyPK);

                    session.saveOrUpdate(wordCategoryFrequency);
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

    public Map<Integer, CategoryTrainingData> setupCategoryTrainingData(){
        try {
            session = HibernateConfiguration.getSessionFactory().openSession();
            session.beginTransaction();
            CategoryDAO categoryDAO = new CategoryDAO();
            Map<Integer, Category> categoryMap = categoryDAO.getCategoryAsMap();
            Map<Integer, CategoryTrainingData> trainingDataMap = new HashMap<Integer, CategoryTrainingData>();

            for(Integer key : categoryMap.keySet()){
                trainingDataMap.put(key, new CategoryTrainingData());
            }

            List<WordCategoryFrequency> wordFrequencies = getWordFrequencyList();
            int categoryId;
            String word;
            int wordCount;
            for(WordCategoryFrequency wf: wordFrequencies){
                word = wf.getWordCategoryFrequencyPK().getWord();
                categoryId = wf.getWordCategoryFrequencyPK().getCategory().getId();
                wordCount = wf.getCount();
                trainingDataMap.get(categoryId).setCategory(categoryMap.get(categoryId));
                trainingDataMap.get(categoryId).getWordFrequency().put(word, wordCount);
            }

            session.getTransaction().commit();
            return trainingDataMap;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            System.out.println("setupCategoryTrainingData: " + ex.getMessage());
            return new HashMap<Integer, CategoryTrainingData>();
        } finally {
            session.close();
        }
    }

    public int occurenceAllCategory(String word) {
        String sql = "SELECT sum(count) FROM word_category_frequency WHERE word = '" + word + "' GROUP BY word";
        try {
            session = HibernateConfiguration.getSessionFactory().openSession();
            session.beginTransaction();
            SQLQuery query = session.createSQLQuery(sql);
            List list = query.list();
            session.getTransaction().commit();

            if(list.size() > 0) {
                BigInteger b = (BigInteger) list.get(0);
                return b.intValue() + 1;
            } else {
                return 1;
            }
        } catch (Exception e) {
            System.out.println("occurenceAllCategory:" + e.getMessage());
            e.printStackTrace();
            session.getTransaction().rollback();
            return 1;
        } finally {
            session.close();
        }
    }

    public int occurenceOnCategory(String word, int id) {
        String sql = "SELECT count FROM word_category_frequency WHERE word = '" + word + "' AND category = " + id;
        return  findCount(sql);
    }

    public int maxOccurenceOnCategory(int id) {
        String sql = "SELECT max(count) as count FROM word_category_frequency where category = " + id;
        return findCount(sql);
    }

    private int findCount(String sql) {
        try {
            session = HibernateConfiguration.getSessionFactory().openSession();
            session.beginTransaction();
            SQLQuery query = session.createSQLQuery(sql);
            List list = query.list();
            session.getTransaction().commit();

            if(list.size() > 0)
                return (Integer) list.get(0);
            else
                return 1;
        } catch (Exception e) {
            System.out.println("findCount:" + e.getMessage());
            e.printStackTrace();
            session.getTransaction().rollback();
            return 1;
        } finally {
            session.close();
        }
    }
}

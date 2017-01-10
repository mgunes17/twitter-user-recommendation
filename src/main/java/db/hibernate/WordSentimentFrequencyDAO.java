package db.hibernate;

import configuration.HibernateConfiguration;
import db.compositekey.WordCategoryFrequencyPK;
import db.compositekey.WordSentimentFrequencyPK;
import db.model.Sentiment;
import db.model.WordCategoryFrequency;
import db.model.WordSentimentFrequency;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import training.CategoryTrainingData;
import training.SentimentTrainingData;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ercan on 24.12.2016.
 */
public class WordSentimentFrequencyDAO extends AbstractDAO {
    private Session session;

    public List<WordSentimentFrequency> getMaxWords(int id) {
        String query = "SELECT * FROM word_sentiment_frequency WHERE sentiment = " + id + " ORDER BY count desc LIMIT 10 ";
        return getRowsBySQLQuery(WordSentimentFrequency.class, query);
    }

    public List<WordSentimentFrequency> getWordFrequencyList(){
        return getAllRows("WordSentimentFrequency");
    }

    public boolean saveWordMap(Map<Integer, SentimentTrainingData> trainingDataMap) {
        try {
            session = HibernateConfiguration.getSessionFactory().openSession();
            session.beginTransaction();
            int i = 0;
            for(Integer key: trainingDataMap.keySet()) {
                for(String s: trainingDataMap.get(key).getWordFrequency().keySet()) {
                    WordSentimentFrequency wordSentimentFrequency = new WordSentimentFrequency();
                    wordSentimentFrequency.setId(++i);
                    wordSentimentFrequency.setCount(trainingDataMap.get(key).getWordFrequency().get(s));
                    WordSentimentFrequencyPK wordSentimentFrequencyPK = new WordSentimentFrequencyPK();
                    wordSentimentFrequencyPK.setSentiment(trainingDataMap.get(key).getSentiment());
                    wordSentimentFrequencyPK.setWord(s);
                    wordSentimentFrequency.setWordSentimentFrequencyPK(wordSentimentFrequencyPK);

                    session.saveOrUpdate(wordSentimentFrequency);
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


    public Map<Integer, SentimentTrainingData> setupSentimentTrainingData(){
        try {
            session = HibernateConfiguration.getSessionFactory().openSession();
            session.beginTransaction();
            SentimentDAO sentimentDAO = new SentimentDAO();
            Map<Integer, Sentiment> sentimentMap = sentimentDAO.getSentimentAsMap();
            Map<Integer, SentimentTrainingData> trainingDataMap = new HashMap<Integer, SentimentTrainingData>();

            for(Integer key : sentimentMap.keySet()){
                trainingDataMap.put(key, new SentimentTrainingData());
            }

            List<WordSentimentFrequency> wordFrequencies = getWordFrequencyList();
            int sentimentId;
            String word;
            int wordCount;
            for(WordSentimentFrequency wf: wordFrequencies){
                word = wf.getWordSentimentFrequencyPK().getWord();
                sentimentId = wf.getWordSentimentFrequencyPK().getSentiment().getId();
                wordCount = wf.getCount();
                trainingDataMap.get(sentimentId).setSentiment(sentimentMap.get(sentimentId));
                trainingDataMap.get(sentimentId).getWordFrequency().put(word, wordCount);
            }

            session.getTransaction().commit();
            return trainingDataMap;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            System.out.println("setupSentimentTrainingData: " + ex.getMessage());
            return new HashMap<Integer, SentimentTrainingData>();
        } finally {
            session.close();
        }
    }

    public int occurenceOnAllSentiment(String word) {
        String sql = "SELECT sum(count) FROM word_sentiment_frequency WHERE word = '" + word + "' GROUP BY word";
        try {
            session = HibernateConfiguration.getSessionFactory().openSession();
            session.beginTransaction();
            SQLQuery query = session.createSQLQuery(sql);
            List list = query.list();
            session.getTransaction().commit();

            if(list.size() != 0) {
                BigInteger b = (BigInteger) list.get(0);
                return b.intValue() + 1;
            } else {
                return 1;
            }
        } catch (Exception e) {
            System.out.println("WordSentimentFrequencyDAO:" + e.getMessage());
            e.printStackTrace();
            session.getTransaction().rollback();
            return 1;
        } finally {
            session.close();
        }
    }


    public int maxOccurenceOnSentiment(int id) {
        String sql = "SELECT max(count) as count FROM word_sentiment_frequency where sentiment = " + id;
        return findCount(sql);
    }

    public int occurenceOnSentiment(String word, int id) {
        String sql = "SELECT count FROM word_sentiment_frequency WHERE word = '" + word + "' AND sentiment = " + id;
        return  findCount(sql);
    }

    private int findCount(String sql) {
        try {
            session = HibernateConfiguration.getSessionFactory().openSession();
            session.beginTransaction();
            SQLQuery query = session.createSQLQuery(sql);
            List list = query.list();
            session.getTransaction().commit();

            if(list.size() != 0)
                return (Integer) list.get(0);
            else
                return 1;
        } catch (Exception e) {
            System.out.println("WordSentimentFrequencyDAO:" + e.getMessage());
            e.printStackTrace();
            session.getTransaction().rollback();
            return 1;
        } finally {
            session.close();
        }
    }
}

package db.hibernate;

import configuration.HibernateConfiguration;
import db.compositekey.WordCategoryFrequencyPK;
import db.model.Category;
import db.model.WordCategoryFrequency;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import statistics.TopWord;
import training.CategoryTrainingData;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
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

    public List<WordCategoryFrequency> getMaxWords(int id) {
        String query = "SELECT * FROM word_category_frequency WHERE category = " + id + " ORDER BY count desc LIMIT 10 ";
        return getRowsBySQLQuery(WordCategoryFrequency.class, query);
    }

    public List<TopWord> getTopList() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/user_recommendation","postgres", "postgres");

            Statement statement = connection.createStatement();
            String query = "SELECT word, sum(count) FROM word_category_frequency WHERE category <> 7 GROUP BY word ORDER BY sum(count) DESC LIMIT 20 ";

            ResultSet resultSet = statement.executeQuery(query);
            List<TopWord> topWords = new ArrayList<TopWord>();

            while(resultSet.next()){
                String word = resultSet.getString(1);
                int count = resultSet.getInt(2);
                topWords.add(new TopWord(word, count));
            }
            connection.close();
            return topWords;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        /*String query = "SELECT word, sum(count) FROM word_category_frequency WHERE category <> 7 GROUP BY word ORDER BY sum(count) DESC LIMIT 20 ";
        List<WordCategoryFrequency> wordCategoryFrequencyList =  getRowsBySQLQuery(WordCategoryFrequency.class, query);

        int count = 0;
        List<TopWord> topWords = new ArrayList<TopWord>();
        for(WordCategoryFrequency wcf: wordCategoryFrequencyList){
            query = "SELECT * FROM word_category_frequency WHERE word = '" + wcf.getWordCategoryFrequencyPK().getWord() + "'";
            List<WordCategoryFrequency> list =  getRowsBySQLQuery(WordCategoryFrequency.class, query);
            for(WordCategoryFrequency w: list){
                count += w.getCount();
            }
            topWords.add(new TopWord(wcf.getWordCategoryFrequencyPK().getWord(), count));
            count = 0;
        }*/
    }
}

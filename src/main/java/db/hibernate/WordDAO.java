package db.hibernate;

import configuration.HibernateConfiguration;
import db.model.AllWords;
import db.model.Sentiment;
import db.model.StopWords;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

public class WordDAO extends AbstractDAO{
    private Session session;

    public boolean isWordExist(String word) {
        String hql = "FROM AllWords WHERE word = :word";
        return isObjectExist(word, hql);
    }

    public boolean isStopWord(String word) {
        String hql = "FROM StopWords WHERE word = :word";
        return isObjectExist(word, hql);
    }

    public List<StopWords> getStopWordsList(){
        return getAllRows("StopWords");
    }

    public Set<String> getStopWords(){
        List<StopWords> stopWords = getStopWordsList();
        Set<String> stopWordSet = new HashSet<String>();

        for(StopWords sw: stopWords){
            stopWordSet.add(sw.getWord());
        }
        return stopWordSet;
    }



    public boolean isObjectExist(String word, String hql) {
        session = HibernateConfiguration.getSessionFactory().openSession();

        try {
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

    public List<AllWords> getWordsWithFirstLetter(char firstLetter, int length) {
        session = HibernateConfiguration.getSessionFactory().openSession();

        try {
            Query query = session.createQuery("FROM AllWords " +
                    "WHERE word " +
                    "LIKE '" + firstLetter + "%'" + " " +
                    "and char_length(word) > " + (length - 2) +
                    "and char_length(word) < " + (length + 1));
            List<AllWords> wordList = query.list();
            return wordList;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}

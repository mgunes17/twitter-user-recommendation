package db.hibernate;


import configuration.HibernateConfiguration;
import db.compositekey.AccountWordAnalyzePK;
import db.model.AccountWordyAnalyze;
import db.model.Category;
import db.model.Sentiment;
import db.model.WordSentimentFrequency;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AccountWordAnalyzeDAO extends AbstractDAO{
    private Session session;

    public boolean saveMap(Map<String, HashMap<Category, Integer>> map, String username) {
        try {
            session = HibernateConfiguration.getSessionFactory().openSession();
            session.beginTransaction();
            int i = 0;

            for(String s: map.keySet()){
                for(Category category: map.get(s).keySet()){
                    AccountWordAnalyzePK accountWordyAnalyzePK = new AccountWordAnalyzePK();
                    AccountWordyAnalyze accountWordyAnalyze = new AccountWordyAnalyze();
                    accountWordyAnalyzePK.setCategory(category);
                    accountWordyAnalyzePK.setUserName(username);
                    accountWordyAnalyzePK.setWord(s);
                    accountWordyAnalyze.setPk(accountWordyAnalyzePK);
                    if(map.get(s).get(category) > 0){
                        accountWordyAnalyze.setSentiment(new Sentiment(1));
                    } else if(map.get(s).get(category) < 0){
                        accountWordyAnalyze.setSentiment(new Sentiment(2));
                    } else {
                        accountWordyAnalyze.setSentiment(new Sentiment(3));
                    }
                    session.saveOrUpdate(accountWordyAnalyze);
                }
            }

            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            System.out.println("saveMap: " + ex.getMessage());
            return false;
        } finally {
            session.close();
        }
    }

    public boolean saveSet(Set<AccountWordyAnalyze> set) {

        try {
            session = HibernateConfiguration.getSessionFactory().openSession();
            session.beginTransaction();

            for(AccountWordyAnalyze s: set){
                AccountWordAnalyzePK accountWordyAnalyzePK = new AccountWordAnalyzePK();
                AccountWordyAnalyze accountWordyAnalyze = new AccountWordyAnalyze();
                accountWordyAnalyzePK.setCategory(s.getPk().getCategory());
                accountWordyAnalyzePK.setUserName(s.getPk().getUserName());
                accountWordyAnalyzePK.setWord(s.getPk().getWord());
                accountWordyAnalyze.setPk(accountWordyAnalyzePK);
                accountWordyAnalyze.setSentiment(s.getSentiment());
                session.saveOrUpdate(accountWordyAnalyze);
            }

            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            System.out.println("saveSet: " + ex.getMessage());
            return false;
        } finally {
            session.close();
        }
    }
}

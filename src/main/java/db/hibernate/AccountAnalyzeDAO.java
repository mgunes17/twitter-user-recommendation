package db.hibernate;

import configuration.HibernateConfiguration;
import db.compositekey.AccountAnalyzePK;
import db.model.AccountAnalyze;
import db.model.Category;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

/**
 * Created by mgunes on 06.01.2017.
 */
public class AccountAnalyzeDAO extends AbstractDAO {
    private Session session;
    public boolean saveAnalyzeList(List<AccountAnalyze> accountAnalyzes) {
        return saveList(accountAnalyzes);
    }

    public boolean saveUserMap(Map<String, List<Integer>> map) {
        try {
            session = HibernateConfiguration.getSessionFactory().openSession();
            session.beginTransaction();

            for(String usr: map.keySet()){
                int i = 1;
                for(Integer count: map.get(usr)){
                    AccountAnalyze accountAnalyze = new AccountAnalyze();
                    AccountAnalyzePK accountAnalyzePK = new AccountAnalyzePK();

                    accountAnalyzePK.setUserName(usr);
                    accountAnalyzePK.setCategory(new Category(i));
                    accountAnalyze.setPk(accountAnalyzePK);
                    accountAnalyze.setWeight(count);
                    i++;
                    session.saveOrUpdate(accountAnalyze);
                }
            }

            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            System.out.println("saveUserMap: " + ex.getMessage());
            return false;
        } finally {
            session.close();
        }
    }

    public List<AccountAnalyze> getUserAnalyze(String username) {
        /*try {
            session = HibernateConfiguration.getSessionFactory().openSession();
            session.beginTransaction();

            String sql = "SELECT * FROM account_analyze WHERE user_name = '" + username + "' order by weight desc\n";
            SQLQuery query = session.createSQLQuery(sql);
            List<AccountAnalyze> list = query.list();

            session.getTransaction().commit();
            return list;
        } catch (Exception ex) {
            System.out.println("getUserAnalyze: " + ex.getMessage());
            return null;
        } finally {
            session.close();
        }*/

        String sql = "SELECT * FROM account_analyze WHERE user_name = '" + username + "' order by weight desc\n";
        return  (List<AccountAnalyze>) getRowsBySQLQuery(AccountAnalyze.class, sql);
    }
}

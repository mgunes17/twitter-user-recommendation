package db.hibernate;

import db.model.Recommendation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ercan on 12.01.2017.
 */
public class RecommendationDAO extends AbstractDAO {
    public List<Recommendation> getRecommendationList(String userName) {
        List<Recommendation> recommendations = new ArrayList<Recommendation>();

        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/user_recommendation", "postgres", "postgres");

            Statement statement = connection.createStatement();
            String query = "select a2.user_name, count(*) from account_word_analyze a1, account_word_analyze a2\n" +
                    "where a1.user_name = '" + userName +"' and a1.category = 3 and a1.word = a2.word\n" +
                    "and a1.sentiment = a2.sentiment and a2.user_name in (\n" +
                    "\tselect distinct a3.user_name\n" +
                    "\tfrom account_analyze a3, account_analyze a4\n" +
                    "\twhere a3.user_name = a4.user_name\n" +
                    "\tand a3.category = 3\n" +
                    "\tand a3.user_name <> '" + userName +"'\n" +
                    "\tand a3.weight =  (select max(weight) \n" +
                    "\t\t\t  from account_analyze a5 \n" +
                    "\t\t\t  where a5.user_name = a3.user_name)\n" +
                    "\t\t\t  and a3.category = 3\n" +
                    "\t\t\t  ) group by a2.user_name order by count(*) desc LIMIT 8";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String username = resultSet.getString(1);
                int count = resultSet.getInt(2);
                Recommendation rec = new Recommendation();
                rec.setUserName(username);
                rec.setCount(count);
                recommendations.add(rec);
            }
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recommendations;
    }
}

package analyze;

import configuration.HibernateConfiguration;
import db.compositekey.AccountAnalyzePK;
import db.compositekey.AccountWordAnalyzePK;
import db.hibernate.*;
import db.model.*;
import org.hibernate.Session;
import parsing.ParseAlgorithm;
import training.TfIdf;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet(name = "SaveUserServlet", urlPatterns = {"/saveuser"})
public class SaveUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        List<String> usernames = new ArrayList<String>();
        PlainTweetDAO plainTweetDAO = new PlainTweetDAO();
        ParsedTweetDAO parsedTweetDAO = new ParsedTweetDAO();

        List<ParsedTweet> parsedTweetList = parsedTweetDAO.getAllRows("ParsedTweet");
        Map<Long, ParsedTweet> parsedTweetMap = new HashMap<Long, ParsedTweet>();

        for(ParsedTweet pt: parsedTweetList){
            parsedTweetMap.put(pt.getId(), pt);
        }

        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/user_recommendation", "postgres", "postgres");

            Statement statement = connection.createStatement();
            String query = "select screen_name, count(*) from plain_tweet group by screen_name having count(*) > 100";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String username = resultSet.getString(1);
                usernames.add(username);
            }
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Set<AccountWordyAnalyze> accountWordyAnalyzeSet = new HashSet<AccountWordyAnalyze>();
        System.out.println(usernames.size());

        Session session = HibernateConfiguration.getSessionFactory().openSession();
        session.beginTransaction();

        for(String str : usernames){
            List<PlainTweet> plainTweetList = plainTweetDAO.getByUsername(str);
            System.out.println(str);

            for(PlainTweet pt: plainTweetList){
                ParsedTweet parsedTweet = parsedTweetMap.get(pt.getId());

                if(parsedTweet != null){
                    int value;
                    int sentiment = parsedTweet.getSentiment().getId();
                    int category = parsedTweet.getCategory().getId();

                    String [] tweetWords = parsedTweet.getOrderedWords().split("-");
                    for(String s:tweetWords){
                        AccountWordAnalyzePK accountWordyAnalyzePK = new AccountWordAnalyzePK();
                        AccountWordyAnalyze accountWordyAnalyze = new AccountWordyAnalyze();
                        accountWordyAnalyzePK.setCategory(new Category(category));
                        accountWordyAnalyzePK.setUserName(str);
                        accountWordyAnalyzePK.setWord(s);
                        accountWordyAnalyze.setPk(accountWordyAnalyzePK);
                        accountWordyAnalyze.setSentiment(new Sentiment(sentiment));
                        try{
                            session.saveOrUpdate(accountWordyAnalyze);
                        } catch (Exception ex){

                        }
                        //accountWordyAnalyzeSet.add(accountWordyAnalyze);
                    }
                }
            }
        }
        session.getTransaction().commit();

        //AccountWordAnalyzeDAO accountWordAnalyzeDAO = new AccountWordAnalyzeDAO();
        //accountWordAnalyzeDAO.saveSet(accountWordyAnalyzeSet);

        //HttpSession session = request.getSession();
        response.sendRedirect("hesap-analiz.jsp");
    }
}

package analyze;

import collection.FetchTweet;
import db.compositekey.AccountAnalyzePK;
import db.hibernate.AccountAnalyzeDAO;
import db.hibernate.AccountWordAnalyzeDAO;
import db.hibernate.CategoryDAO;
import db.hibernate.RecommendationDAO;
import db.model.*;
import parsing.ParseAlgorithm;
import training.TfIdf;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(name = "AccountAnalyzeServlet", urlPatterns = {"/accountanalyze"})
public class AccountAnalyzeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        FetchTweet fetchTweet = new FetchTweet(username, 200);
        List<PlainTweet> plainTweetList = fetchTweet.getTweetsByUsername();
        StopWordSet stopWordSet = new StopWordSet();


        ParseAlgorithm parseAlgorithm = new ParseAlgorithm();
        parseAlgorithm.parsePlainTweets(plainTweetList);
        List<ParsedTweet> parsedTweets = parseAlgorithm.getParsedTweets();

        TfIdf tfIdf = new TfIdf();
        int[] categoryWeight = new int[8];

        for (int i = 1; i < 8; i++) {
            categoryWeight[i] = 0;
        }

        // <word, Map<Category, Count>> sentiment - ise count-- , sentiment + ise count++
        Map<String, HashMap<Category, Integer>> map = new HashMap<String, HashMap<Category, Integer>>();
        CategoryDAO categoryDAO = new CategoryDAO();
        Map<Integer, Category> categoryMap = categoryDAO.getCategoryAsMap();

        int value;
        int sentiment;
        int category;
        for (ParsedTweet p : parsedTweets) {
            String [] tweetWords = p.getOrderedWords().split("-");

            tfIdf.setTweet(p.getOrderedWords().replaceAll("-", " "));
            category = tfIdf.findCategoryID();
            sentiment = tfIdf.findSentimentID();
            categoryWeight[category]++;

            for(String s:tweetWords){
                if(!map.containsKey(s)) {
                    map.put(s, new HashMap<Category, Integer>());
                }

                if(map.get(s).containsKey(categoryMap.get(category))){
                    value = map.get(s).get(categoryMap.get(category));
                } else {
                    value = 0;
                }

                if(sentiment == 1){
                    value += 1;
                } else if(sentiment == 2) {
                    value -= 1;
                }
                map.get(s).put(categoryMap.get(category), value);
            }
        }
        AccountWordAnalyzeDAO accountWordAnalyzeDAO = new AccountWordAnalyzeDAO();
        accountWordAnalyzeDAO.saveMap(map, username);

        List<AccountAnalyze> accountAnalyzes = new ArrayList<AccountAnalyze>();

        for (int i = 1; i < 8; i++) {
            AccountAnalyze accountAnalyze = new AccountAnalyze();
            AccountAnalyzePK pk = new AccountAnalyzePK();

            pk.setCategory(new Category(i));
            pk.setUserName(username);
            accountAnalyze.setPk(pk);
            accountAnalyze.setWeight((categoryWeight[i] * 100) / plainTweetList.size());
            accountAnalyzes.add(accountAnalyze);
        }

        AccountAnalyzeDAO accountAnalyzeDAO = new AccountAnalyzeDAO();
        accountAnalyzeDAO.saveAnalyzeList(accountAnalyzes);



        /*
            En yakın kullanıcıların listesini çek

            Kendi kayıtlarını sırayla çek sorguda Diğer i hariç
        */

        List<AccountAnalyze> analyzes = accountAnalyzeDAO.getUserAnalyze(username);
        List<Recommendation> recommendations = new RecommendationDAO().getRecommendationList(username);

        HttpSession session = request.getSession();
        session.setAttribute("analyze", analyzes);
        session.setAttribute("recommendations", recommendations);
        response.sendRedirect("hesap-analiz.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

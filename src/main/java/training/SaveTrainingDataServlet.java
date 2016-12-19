package training;

import hibernate.CategoryDAO;
import hibernate.ParsedTweetDAO;
import hibernate.SentimentDAO;
import hibernate.WordFrequencyDAO;
import model.Category;
import model.ParsedTweet;
import model.Sentiment;
import model.WordFrequency;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ercan on 18.12.2016.
 */

@WebServlet(name = "SaveTrainingDataServlet", urlPatterns = {"/savetrainingdata"})
public class SaveTrainingDataServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        List<ParsedTweet> tweetList = (List<ParsedTweet>) session.getAttribute("tweetList");

        String[] categoryResults = request.getParameterValues("category");
        String[] sentimentResults = request.getParameterValues("sentiment");

        SentimentDAO sentimentDAO = new SentimentDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        Map<Integer, Sentiment> sentimentMap = sentimentDAO.getSentimentAsMap();
        Map<Integer, Category> categoryMap = categoryDAO.getCategoryAsMap();

        Map<Integer, TrainingData> trainingDataMap = new HashMap<Integer, TrainingData>();

        for(Integer key : categoryMap.keySet()){
            trainingDataMap.put(key, new TrainingData());
        }

        int category;
        int sentiment;
        for(int i = 0; i < categoryResults.length; i++){
            String [] categoryData = categoryResults[i].split("_");
            String [] sentimentData = sentimentResults[i].split("_");
            category = Integer.parseInt(categoryData[1]);
            sentiment = Integer.parseInt(sentimentData[1]);

            tweetList.get(i).setCategory(categoryMap.get(category));
            tweetList.get(i).setSentiment(sentimentMap.get(sentiment));

            String [] tweetWords = categoryData[0].split("-");

            Map<String, Integer> wordFrequencyMap = trainingDataMap.get(category).getWordFrequency();

            int value;
            for(String word: tweetWords){
                word = word.trim();
                if(wordFrequencyMap.containsKey(word)){
                    value = wordFrequencyMap.get(word);
                    wordFrequencyMap.put(word, ++value);
                } else {
                    wordFrequencyMap.put(word, 1);
                }
            }
            trainingDataMap.get(category).setCategory(categoryMap.get(category));
            trainingDataMap.get(category).setWordFrequency(wordFrequencyMap);
        }

        WordFrequencyDAO wordFrequencyDAO = new WordFrequencyDAO();
        wordFrequencyDAO.saveWordMap(trainingDataMap);

        ParsedTweetDAO parsedTweetDAO = new ParsedTweetDAO();
        parsedTweetDAO.saveParsedList(tweetList);

        List<Category> categoryList = new CategoryDAO().getCategoryList();
        session.setAttribute("categoryList", categoryList);

        response.sendRedirect("kategori-kelime.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

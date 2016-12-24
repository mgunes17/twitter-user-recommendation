package training;

import db.hibernate.*;
import db.model.Category;
import db.model.ParsedTweet;
import db.model.Sentiment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

        WordCategoryFrequencyDAO wordCategoryFrequencyDAO = new WordCategoryFrequencyDAO();
        Map<Integer, CategoryTrainingData> categoryTrainingDataMap = wordCategoryFrequencyDAO.setupCategoryTrainingData();

        WordSentimentFrequencyDAO wordSentimentFrequencyDAO = new WordSentimentFrequencyDAO();
        Map<Integer, SentimentTrainingData> sentimentTrainingDataMap = wordSentimentFrequencyDAO.setupSentimentTrainingData();

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

            Map<String, Integer> wordFrequencyMapForCategory = categoryTrainingDataMap.get(category).getWordFrequency();
            Map<String, Integer> wordFrequencyMapForSentiment = sentimentTrainingDataMap.get(sentiment).getWordFrequency();

            int value;
            for(String word: tweetWords){
                word = word.trim();
                if(wordFrequencyMapForCategory.containsKey(word)){
                    value = wordFrequencyMapForCategory.get(word);
                    wordFrequencyMapForCategory.put(word, ++value);
                } else {
                    wordFrequencyMapForCategory.put(word, 1);
                }

                if(wordFrequencyMapForSentiment.containsKey(word)){
                    value = wordFrequencyMapForSentiment.get(word);
                    wordFrequencyMapForSentiment.put(word, ++value);
                } else {
                    wordFrequencyMapForSentiment.put(word, 1);
                }
            }
            sentimentTrainingDataMap.get(sentiment).setSentiment(sentimentMap.get(sentiment));
            sentimentTrainingDataMap.get(sentiment).setWordFrequency(wordFrequencyMapForSentiment);

            categoryTrainingDataMap.get(category).setCategory(categoryMap.get(category));
            categoryTrainingDataMap.get(category).setWordFrequency(wordFrequencyMapForCategory);
        }

        wordCategoryFrequencyDAO.saveWordMap(categoryTrainingDataMap);
        wordSentimentFrequencyDAO.saveWordMap(sentimentTrainingDataMap);

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

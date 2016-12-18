package training;

import hibernate.CategoryDAO;
import hibernate.SentimentDAO;
import model.Category;
import model.Sentiment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ercan on 18.12.2016.
 */

@WebServlet(name = "SaveTrainingDataServlet", urlPatterns = {"/savetrainingdata"})
public class SaveTrainingDataServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

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

        for(int i = 0; i < categoryResults.length; i++){
            String [] categoryData = categoryResults[i].split("_");
            String [] sentimentData = sentimentResults[i].split("_");
            int category = Integer.parseInt(categoryData[1]);
            int sentiment = Integer.parseInt(sentimentData[1]);

            String [] tweetWords = categoryData[0].split("-");

            Map<String, Integer> wordFrequencyMap = trainingDataMap.get(category).getWordFrequency();

            int value;
            for(String word: tweetWords){
                word.trim();
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

        System.out.println("gg");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

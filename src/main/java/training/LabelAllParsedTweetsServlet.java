package training;

import db.hibernate.*;
import db.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "LabelAllParsedTweetsServlet", urlPatterns = {"/labelparsedtweets"})
public class LabelAllParsedTweetsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        StopWordSet stopWordSet = new StopWordSet();

        WordCategoryFrequencyDAO wordCategoryFrequencyDAO = new WordCategoryFrequencyDAO();
        Map<Integer, CategoryTrainingData> categoryTrainingDataMap = wordCategoryFrequencyDAO.setupCategoryTrainingData();

        WordSentimentFrequencyDAO wordSentimentFrequencyDAO = new WordSentimentFrequencyDAO();
        Map<Integer, SentimentTrainingData> sentimentTrainingDataMap = wordSentimentFrequencyDAO.setupSentimentTrainingData();

        SentimentDAO sentimentDAO = new SentimentDAO();
        Map<Integer, Sentiment> sentimentMap = sentimentDAO.getSentimentAsMap();

        CategoryDAO categoryDAO = new CategoryDAO();
        Map<Integer, Category> categoryMap = categoryDAO.getCategoryAsMap();

        ParsedTweetDAO parsedTweetDAO = new ParsedTweetDAO();
        List<ParsedTweet> parsedTweetList = parsedTweetDAO.getRandomParsedTweet(5000);

        int category;
        int sentiment;
        for(ParsedTweet parsedTweet: parsedTweetList){
            String [] tweetWords = parsedTweet.getOrderedWords().split("-");

            String tweet = parsedTweet.getOrderedWords().replaceAll("-", " ");
            TfIdf tfIdf = new TfIdf(tweet);
            category = tfIdf.findCategoryID();
            sentiment = tfIdf.findSentimentID();

            parsedTweet.setCategory(categoryMap.get(category));
            parsedTweet.setSentiment(sentimentMap.get(sentiment));

            Map<String, Integer> wordFrequencyMapForCategory = categoryTrainingDataMap.get(category).getWordFrequency();
            Map<String, Integer> wordFrequencyMapForSentiment = sentimentTrainingDataMap.get(sentiment).getWordFrequency();

            int value;
            for(String word: tweetWords){

                if(!StopWordSet.STOP_WORD_SET.contains(word)){
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
            }
            System.out.println("-----");

            sentimentTrainingDataMap.get(sentiment).setSentiment(sentimentMap.get(sentiment));
            sentimentTrainingDataMap.get(sentiment).setWordFrequency(wordFrequencyMapForSentiment);

            categoryTrainingDataMap.get(category).setCategory(categoryMap.get(category));
            categoryTrainingDataMap.get(category).setWordFrequency(wordFrequencyMapForCategory);
        }

        wordCategoryFrequencyDAO.saveWordMap(categoryTrainingDataMap);
        wordSentimentFrequencyDAO.saveWordMap(sentimentTrainingDataMap);
        parsedTweetDAO.saveParsedList(parsedTweetList);
    }
}

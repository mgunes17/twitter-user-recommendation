package training;

import db.model.Sentiment;

import java.util.HashMap;
import java.util.Map;

public class SentimentTrainingData {
    private Sentiment sentiment;
    private Map<String, Integer> wordFrequency;

    public SentimentTrainingData(){
        wordFrequency = new HashMap<String, Integer>();
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public Map<String, Integer> getWordFrequency() {
        return wordFrequency;
    }

    public void setWordFrequency(Map<String, Integer> wordFrequency) {
        this.wordFrequency = wordFrequency;
    }
}

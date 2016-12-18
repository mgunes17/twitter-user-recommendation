package training;

import model.Category;
import model.Sentiment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ercan on 18.12.2016.
 */

public class TrainingData {
    private Category category;
    private Map<String, Integer> wordFrequency;

    public TrainingData(){
        wordFrequency = new HashMap<String, Integer>();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Map<String, Integer> getWordFrequency() {
        return wordFrequency;
    }

    public void setWordFrequency(Map<String, Integer> wordFrequency) {
        this.wordFrequency = wordFrequency;
    }
}

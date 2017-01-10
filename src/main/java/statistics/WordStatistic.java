package statistics;

import db.model.Category;
import db.model.Sentiment;
import db.model.WordCategoryFrequency;
import db.model.WordSentimentFrequency;

import java.util.List;
import java.util.Map;

/**
 * Created by mgunes on 10.01.2017.
 */
public class WordStatistic {
    private Map<Category, List<WordCategoryFrequency>> categoryList;
    private Map<Sentiment, List<WordSentimentFrequency>> sentimentList;
    private List<TopWord> topList;

    public WordStatistic() {
        super();
    }

    public Map<Category, List<WordCategoryFrequency>> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(Map<Category, List<WordCategoryFrequency>> categoryList) {
        this.categoryList = categoryList;
    }

    public Map<Sentiment, List<WordSentimentFrequency>> getSentimentList() {
        return sentimentList;
    }

    public void setSentimentList(Map<Sentiment, List<WordSentimentFrequency>> sentimentList) {
        this.sentimentList = sentimentList;
    }

    public List<TopWord> getTopList() {
        return topList;
    }

    public void setTopList(List<TopWord> topList) {
        this.topList = topList;
    }
}

package db.hibernate;

import db.model.Sentiment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mgunes on 18.12.2016.
 */
public class SentimentDAO extends AbstractDAO {

    public List<Sentiment> getSentimentList(){
        return getAllRows("Sentiment");
    }

    public Map<Integer, Sentiment> getSentimentAsMap(){
        List<Sentiment> sentimentList = getSentimentList();
        Map<Integer, Sentiment> sentimentMap = new HashMap<Integer, Sentiment>();

        for(Sentiment sentiment : sentimentList){
            sentimentMap.put(sentiment.getId(), sentiment);
        }
        return sentimentMap;
    }
}

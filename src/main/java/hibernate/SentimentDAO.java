package hibernate;

import model.Sentiment;

import java.util.List;

/**
 * Created by mgunes on 18.12.2016.
 */
public class SentimentDAO extends AbstractDAO {

    public List<Sentiment> getSentimentList(){
        return getAllRows("Sentiment");
    }
}
